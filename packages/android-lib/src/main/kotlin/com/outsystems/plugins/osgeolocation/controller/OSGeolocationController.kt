package com.outsystems.plugins.osgeolocation.controller

import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.core.location.LocationManagerCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.outsystems.plugins.osgeolocation.model.OSLocationException
import com.outsystems.plugins.osgeolocation.model.OSLocationOptions
import com.outsystems.plugins.osgeolocation.model.OSLocationResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await

/**
 * Entry point in OSGeolocationLib-Android
 *
 */
class OSGeolocationController(
    private val fusedLocationClient: FusedLocationProviderClient,
    private val activityLauncher: ActivityResultLauncher<IntentSenderRequest>
) {
    private lateinit var resolveLocationSettingsResultFlow: MutableSharedFlow<Result<Unit>>
    private val locationCallbacks: MutableMap<String, LocationCallback> = mutableMapOf()
    private val watchIdsBlacklist: MutableList<String> = mutableListOf()

    /**
     * Obtains the device's location using FusedLocationProviderClient.
     * Tries to obtain the last retrieved location, and then gets a fresh one if necessary.
     * @param activity the Android activity from which the location request is being triggered
     * @param options OSLocationOptions object with the options to obtain the location with (e.g. timeout)
     * @return Result<OSLocationResult> object with either the location or an exception to be handled by the caller
     */
    suspend fun getCurrentPosition(
        activity: Activity,
        options: OSLocationOptions
    ): Result<OSLocationResult> {
        try {
            // check timeout
            if (options.timeout <= 0) {
                return Result.failure(OSLocationException.OSLocationInvalidTimeoutException(
                    message = "Timeout needs to be a positive value."
                ))
            }

            val checkResult: Result<Unit> =
                checkLocationPreconditions(activity, options, isSingleLocationRequest = true)
            return if (checkResult.isFailure) {
                Result.failure(
                    checkResult.exceptionOrNull() ?: NullPointerException()
                )
            } else {
                val location = getCurrentLocation(options)
                return Result.success(location.toOSLocationResult())
            }
        } catch (exception: Exception) {
            Log.d(LOG_TAG, "Error fetching location: ${exception.message}")
            return Result.failure(exception)
        }
    }

    /**
     * Function to be called by the client after returning from the activity
     * that is launched when resolving the ResolvableApiException in checkLocationSettings,
     * that prompts the user to enable the location if it is disabled.
     * @param resultCode to determine if the user enabled the location when prompted
     */
    suspend fun onResolvableExceptionResult(resultCode: Int) {
        if (resultCode == Activity.RESULT_OK) {
            resolveLocationSettingsResultFlow.emit(Result.success(Unit))
        } else {
            resolveLocationSettingsResultFlow.emit(
                Result.failure(
                    OSLocationException.OSLocationRequestDeniedException(
                        message = "Request to enable location denied."
                    )
                )
            )
        }
    }

    /**
     * Checks if location services are enabled
     * @param context Context to use when determining if location is enabled
     */
    fun areLocationServicesEnabled(context: Context): Boolean {
        return LocationManagerCompat.isLocationEnabled(context.getSystemService(Context.LOCATION_SERVICE) as LocationManager)
    }

    /**
     * Creates a callback for location updates.
     * @param activity the Android activity from which the location request is being triggered
     * @param options location request options to use
     * @param watchId a unique id identifying the watch
     * @return Flow in which location updates will be emitted
     */
    fun addWatch(activity: Activity, options: OSLocationOptions, watchId: String): Flow<Result<List<OSLocationResult>>> = callbackFlow {

        try {
            val checkResult: Result<Unit> =
                checkLocationPreconditions(activity, options, isSingleLocationRequest = true)
            if (checkResult.isFailure) {
                trySend(
                    Result.failure(checkResult.exceptionOrNull() ?: NullPointerException())
                )
            } else {
                locationCallbacks[watchId] = object : LocationCallback() {
                    override fun onLocationResult(location: LocationResult) {
                        if (watchIdsBlacklist.contains(watchId)) {
                            // received a location update but watch id is in blacklist, so the location updates should be removed
                            val cleared = clearWatch(watchId, addToBlackList = false)
                            if (cleared) {
                                watchIdsBlacklist.remove(watchId)
                            }
                            return
                        }
                        val locations = location.locations.map { currentLocation ->
                            currentLocation.toOSLocationResult()
                        }
                        trySend(Result.success(locations))
                    }
                }
                requestLocationUpdates(options, watchId)
            }
        } catch (exception: Exception) {
            Log.d(LOG_TAG, "Error requesting location updates: ${exception.message}")
            trySend(Result.failure(exception))
        }

        awaitClose {
            clearWatch(watchId)
        }
    }

    /**
     * clears a watch by removing its location update request
     * @param id the watch id
     * @return true if watch was cleared, false if watch was not found
     */
    fun clearWatch(id: String): Boolean = clearWatch(id, addToBlackList = true)

    /**
     * Obtains a fresh device location.
     * @param options location request options to use
     * @return Location object representing the location
     */
    private suspend fun getCurrentLocation(options: OSLocationOptions): Location {

        val locationRequest = CurrentLocationRequest.Builder()
            .setPriority(if (options.enableHighAccuracy) Priority.PRIORITY_HIGH_ACCURACY else Priority.PRIORITY_BALANCED_POWER_ACCURACY)
            .setMaxUpdateAgeMillis(options.maximumAge)
            .setDurationMillis(options.timeout)
            .build()

        return fusedLocationClient.getCurrentLocation(
            locationRequest,
            null
        ).await()
    }

    /**
     * Requests updates of device location.
     *
     * Locations returned in callback associated with watchId
     * @param options location request options to use
     * @param watchId the id for this location request
     */
    private fun requestLocationUpdates(options: OSLocationOptions, watchId: String) {
        val locationRequest = LocationRequest.Builder(options.timeout).apply {
            setMaxUpdateAgeMillis(options.maximumAge)
            setPriority(if (options.enableHighAccuracy) Priority.PRIORITY_HIGH_ACCURACY else Priority.PRIORITY_BALANCED_POWER_ACCURACY)
            if (options.minUpdateInterval != null) {
                setMinUpdateIntervalMillis(options.minUpdateInterval)
            }
        }.build()

        locationCallbacks[watchId]?.let {
            fusedLocationClient.requestLocationUpdates(locationRequest, it, Looper.getMainLooper())
        }
    }

    /**
     * checks if all preconditions for retrieving location are met
     * @param activity the Android activity from which the location request is being triggered
     * @param options location request options to use
     * @param isSingleLocationRequest true if request is for a single location, false if for location updates
     */
    private suspend fun checkLocationPreconditions(
        activity: Activity,
        options: OSLocationOptions,
        isSingleLocationRequest: Boolean
    ): Result<Unit> {
        val playServicesResult = checkGooglePlayServicesAvailable(activity)
        if (playServicesResult.isFailure) {
            return Result.failure(playServicesResult.exceptionOrNull() ?: NullPointerException())
        }

        resolveLocationSettingsResultFlow = MutableSharedFlow()
        val locationSettingsChecked = checkLocationSettings(
            activity,
            options,
            interval = if (isSingleLocationRequest) 0 else options.timeout
        )

        return if (locationSettingsChecked) {
            Result.success(Unit)
        } else {
            resolveLocationSettingsResultFlow.first()
        }
    }

    /**
     * Checks if location is on, as well as other conditions for retrieving device location
     * @param activity the Android activity from which the location request is being triggered
     * @param options location request options to use
     * @param interval interval for requesting location updates; use 0 if meant to retrieve a single location
     * @return true if location was checked and is on, false if it requires user to resolve issue (e.g. turn on location)
     *          If false, the result is returned in `resolveLocationSettingsResultFlow`
     * @throws [OSLocationException.OSLocationSettingsException] if an error occurs that is not resolvable by user
     */
    private suspend fun checkLocationSettings(
        activity: Activity,
        options: OSLocationOptions,
        interval: Long
    ): Boolean {

        val request = LocationRequest.Builder(
            if (options.enableHighAccuracy) Priority.PRIORITY_HIGH_ACCURACY else Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            interval
        ).build()

        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(request)
        val client = LocationServices.getSettingsClient(activity)

        try {
            client.checkLocationSettings(builder.build()).await()
            return true
        } catch (e: ResolvableApiException) {

            // Show the dialog to enable location by calling startResolutionForResult(),
            // and then handle the result in onActivityResult
            val resolutionBuilder: IntentSenderRequest.Builder = IntentSenderRequest.Builder(e.resolution)
            val resolution: IntentSenderRequest = resolutionBuilder.build()

            activityLauncher.launch(resolution)
        } catch (e: Exception) {
            throw OSLocationException.OSLocationSettingsException(
                message = "There is an error with the location settings.",
                cause = e
            )
        }
        return false
    }

    /**
     * Checks if the device has google play services, required to use [FusedLocationProviderClient]
     * @param activity the Android activity from which the location request is being triggered
     * @return true if google play services is available, false otherwise
     */
    private fun checkGooglePlayServicesAvailable(activity: Activity): Result<Unit> {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val status = googleApiAvailability.isGooglePlayServicesAvailable(activity)

        return if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 1)?.show()
                Result.failure(
                    OSLocationException.OSLocationGoogleServicesException(
                        resolvable = true,
                        message = "Google Play Services error user resolvable."
                    )
                )
            } else {
                Result.failure(
                    OSLocationException.OSLocationGoogleServicesException(
                        resolvable = false,
                        message = "Google Play Services error."
                    )
                )
            }
        } else {
            Result.success(Unit)
        }
    }

    /**
     * clears a watch by removing its location update request
     * @param id the watch id
     * @param addToBlackList whether or not the watch id should go in blacklist if not found
     * @return true if watch was cleared, false if watch was not found
     */
    private fun clearWatch(id: String, addToBlackList: Boolean): Boolean {
        val locationCallback = locationCallbacks.remove(key = id)
        return if (locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback)
            true
        } else {
            if (addToBlackList) {
                // It is possible that clearWatch is being called before requestLocationUpdates is triggered (e.g. very low timeout on JavaScript side.)
                //  add to a blacklist in order to remove the location callback in the future
                watchIdsBlacklist.add(id)
            }
            false
        }
    }

    private fun Location.toOSLocationResult(): OSLocationResult = OSLocationResult(
        latitude = this.latitude,
        longitude = this.longitude,
        altitude = this.altitude,
        accuracy =  this.accuracy,
        altitudeAccuracy = if (OSGeolocationBuildConfig.getAndroidSdkVersionCode() >= Build.VERSION_CODES.O) this.verticalAccuracyMeters else null,
        heading = this.bearing,
        speed = this.speed,
        timestamp = this.time
    )

    companion object {
        private const val LOG_TAG = "OSGeolocationController"
    }

}