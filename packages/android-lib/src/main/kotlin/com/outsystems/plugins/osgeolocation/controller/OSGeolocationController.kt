package com.outsystems.plugins.osgeolocation.controller

import android.location.Location
import android.os.SystemClock
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.outsystems.plugins.osgeolocation.model.OSLocationOptions
import com.outsystems.plugins.osgeolocation.model.OSLocationResult
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout

/**
 * Entry point in OSGeolocationLib-Android
 *
 */
class OSGeolocationController(private val fusedLocationClient: FusedLocationProviderClient) {

    /**
     * Obtains the device's location using FusedLocationProviderClient.
     * Tries to obtain the last retrieved location, and then gets a fresh one if necessary.
     * @param options OSLocationOptions object with the options to obtain the location with (e.g. timeout)
     * @return Result<OSLocationResult> object with either the location or an exception to be handled by the caller
     */
    suspend fun getLocation(options: OSLocationOptions): Result<OSLocationResult> {

        try {
            // try to get the last known location
            val lastLocation = getLastLocation(options.timeout)
            val location = if (lastLocation != null && isLocationFresh(lastLocation, options.maximumAge)) {
                lastLocation
            } else {
                // fallback to get a fresh location
                getFreshLocation(options.timeout, options.enableHighAccuracy)
            }

            return Result.success(
                OSLocationResult(
                    location.latitude,
                    location.longitude,
                    location.altitude,
                    location.accuracy,
                    location.bearing,
                    location.speed,
                    location.time
                )
            )

        } catch (exception: TimeoutCancellationException) {
            Log.d(LOG_TAG, "Timed out while fetching location: ${exception.message}")
            return Result.failure(exception)
        } catch (exception: Exception) {
            Log.d(LOG_TAG, "Error fetching location: ${exception.message}")
            return Result.failure(exception)
        }

    }

    /**
     * Obtains the device's last registered location, or null if there is none.
     * @param timeout maximum time to wait while obtaining the location
     * @return Location object representing the location if successful, or null otherwise
     */
    private suspend fun getLastLocation(timeout: Long): Location? {
        return try {
            withTimeout(timeout) {
                fusedLocationClient.lastLocation.await()
            }
        } catch (exception: TimeoutCancellationException) {
            Log.d(LOG_TAG, "Timed out while fetching last location: ${exception.message}")
            null
        } catch (exception: Exception) {
            Log.d(LOG_TAG, "Timed out while fetching location: ${exception.message}")
            println("Error fetching last location: ${exception.message}")
            null
        }
    }

    /**
     * Obtains a fresh device location.
     * @param timeout maximum time to wait while obtaining the location
     * @return Location object representing the location
     */
    private suspend fun getFreshLocation(timeout: Long, enableHighAccuracy: Boolean): Location {
        return withTimeout(timeout) {
            fusedLocationClient.getCurrentLocation(
                if (enableHighAccuracy) Priority.PRIORITY_HIGH_ACCURACY else Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                null
            ).await()
        }
    }

    private fun isLocationFresh(location: Location, maximumAge: Long): Boolean {
        val ageInNanos = maximumAge * 1000000L
        val currentTime = SystemClock.elapsedRealtime()
        val locationTime = location.elapsedRealtimeNanos
        return (currentTime - locationTime) <= ageInNanos
    }

    fun addWatch() {
        //TODO
    }

    fun clearWatch() {
        //TODO
    }

    companion object {
        private const val LOG_TAG = "OSGeolocationController"
    }

}