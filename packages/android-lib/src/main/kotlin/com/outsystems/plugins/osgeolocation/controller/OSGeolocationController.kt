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
    private lateinit var flow: MutableSharedFlow<Result<Unit>>
    private val locationCallbacks: MutableMap<String, LocationCallback> = mutableMapOf()
    private val watchIdsBlacklist: MutableList<String> = mutableListOf()

    /**
     * Obtains the device's location using FusedLocationProviderClient.
     * Tries to obtain the last retrieved location, and then gets a fresh one if necessary.
     * @param options OSLocationOptions object with the options to obtain the location with (e.g. timeout)
     * @return Result<OSLocationResult> object with either the location or an exception to be handled by the caller
     */
    suspend fun getCurrentPosition(
        activity: Activity,
        options: OSLocationOptions
    ): Result<OSLocationResult> {
        try {

            // check play services
            val checkResult = checkGooglePlayServicesAvailable(activity)

            if (checkResult.isFailure) {
                return Result.failure(checkResult.exceptionOrNull() ?: NullPointerException())
            }

            // check timeout
            if (options.timeout <= 0) {
                return Result.failure(OSLocationException.OSLocationInvalidTimeoutException(
                    message = "Timeout needs to be a positive value."
                ))
            }

            flow = MutableSharedFlow()

            if (checkLocationSettings(
                    activity,
                    options,
                    0 // 0 is used because we only want to do one location request
                )) {
                val location = getCurrentLocation(options)
                return Result.success(
                    OSLocationResult(
                        location.latitude,
                        location.longitude,
                        location.altitude,
                        location.accuracy,
                        if (OSGeolocationBuildConfig.getAndroidSdkVersionCode() >= Build.VERSION_CODES.O) location.verticalAccuracyMeters else null,
                        location.bearing,
                        location.speed,
                        location.time
                    )
                )
            }

            val result = flow.first()

            if (result.isSuccess) {
                val location = getCurrentLocation(options)
                return Result.success(
                    OSLocationResult(
                        location.latitude,
                        location.longitude,
                        location.altitude,
                        location.accuracy,
                        if (OSGeolocationBuildConfig.getAndroidSdkVersionCode() >= Build.VERSION_CODES.O) location.verticalAccuracyMeters else null,
                        location.bearing,
                        location.speed,
                        location.time
                    )
                )
            } else {
                return Result.failure(
                    result.exceptionOrNull() ?: NullPointerException()
                )
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
            flow.emit(Result.success(Unit))
        } else {
            flow.emit(
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

    private suspend fun checkLocationSettings(activity: Activity, options: OSLocationOptions, interval: Long): Boolean {

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

    private fun checkGooglePlayServicesAvailable(activity: Activity): Result<Unit> {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val status = googleApiAvailability.isGooglePlayServicesAvailable(activity)

        return if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 1)?.show()
                Result.failure(OSLocationException.OSLocationGoogleServicesException(
                    resolvable = true,
                    message = "Google Play Services error user resolvable."
                ))
            } else {
                Result.failure(OSLocationException.OSLocationGoogleServicesException(
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
     * Creates a callback for location updates.
     * @param options location request options to use
     * @return Location object representing the location
     */
    fun addWatch(activity: Activity, options: OSLocationOptions, watchId: String): Flow<Result<List<OSLocationResult>>> = callbackFlow {

        try {
            // check play services
            val checkResult = checkGooglePlayServicesAvailable(activity)

            if (checkResult.isFailure) {
                trySend(Result.failure(checkResult.exceptionOrNull() ?: NullPointerException()))
            } else {
                flow = MutableSharedFlow()

                if (checkLocationSettings(
                        activity,
                        options,
                        options.timeout
                    )) {
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
                                OSLocationResult(
                                    currentLocation.latitude,
                                    currentLocation.longitude,
                                    currentLocation.altitude,
                                    currentLocation.accuracy,
                                    if (OSGeolocationBuildConfig.getAndroidSdkVersionCode() >= Build.VERSION_CODES.O) currentLocation.verticalAccuracyMeters else null,
                                    currentLocation.bearing,
                                    currentLocation.speed,
                                    currentLocation.time
                                )
                            }
                            trySend(Result.success(locations))
                        }
                    }
                    requestLocationUpdates(options, watchId)
                }

                val result = flow.first()
                if (result.isSuccess) {
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
                                OSLocationResult(
                                    currentLocation.latitude,
                                    currentLocation.longitude,
                                    currentLocation.altitude,
                                    currentLocation.accuracy,
                                    if (OSGeolocationBuildConfig.getAndroidSdkVersionCode() >= Build.VERSION_CODES.O) currentLocation.verticalAccuracyMeters else null,
                                    currentLocation.bearing,
                                    currentLocation.speed,
                                    currentLocation.time
                                )
                            }
                            trySend(Result.success(locations))
                        }
                    }
                    requestLocationUpdates(options, watchId)
                } else {
                    trySend(
                        Result.failure(
                            result.exceptionOrNull() ?: NullPointerException()
                        )
                    )
                }
            }
        } catch (exception: Exception) {
            Log.d(LOG_TAG, "Error requesting location updates: ${exception.message}")
            trySend(Result.failure(exception))
        }

        awaitClose {
            clearWatch(watchId)
        }
    }

    private fun requestLocationUpdates(options: OSLocationOptions, watchId: String) {
        val locationRequest = LocationRequest.Builder(options.timeout).apply {
            setMaxUpdateAgeMillis(options.maximumAge)
            setPriority(if (options.enableHighAccuracy) Priority.PRIORITY_HIGH_ACCURACY else Priority.PRIORITY_BALANCED_POWER_ACCURACY)
            if (options.minUpdateInterval != null) {
                setMinUpdateIntervalMillis(options.minUpdateInterval)
            }
        }.build()

        locationCallbacks[watchId]?.let {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper()
            )
        }
    }

    /**
     * clears a watch by removing its location update request
     * @param id the watch id
     * @return true if watch was cleared, false if watch was not found
     */
    fun clearWatch(id: String): Boolean = clearWatch(id, addToBlackList = true)

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

    companion object {
        private const val LOG_TAG = "OSGeolocationController"
    }

}