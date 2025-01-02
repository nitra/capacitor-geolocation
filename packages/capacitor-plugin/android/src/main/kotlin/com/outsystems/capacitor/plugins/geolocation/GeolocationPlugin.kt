package com.outsystems.capacitor.plugins.geolocation

import android.Manifest
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import com.getcapacitor.JSObject
import com.getcapacitor.PermissionState
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin
import com.getcapacitor.annotation.Permission
import com.getcapacitor.annotation.PermissionCallback
import com.google.android.gms.location.LocationServices
import com.outsystems.plugins.osgeolocation.controller.OSGLOCController
import com.outsystems.plugins.osgeolocation.model.OSGLOCException
import com.outsystems.plugins.osgeolocation.model.OSGLOCLocationOptions
import com.outsystems.plugins.osgeolocation.model.OSGLOCLocationResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@CapacitorPlugin(
    name = "GeolocationPlugin",
    permissions = [Permission(
        strings = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION],
        alias = GeolocationPlugin.LOCATION_ALIAS
    ), Permission(
        strings = [Manifest.permission.ACCESS_COARSE_LOCATION],
        alias = GeolocationPlugin.COARSE_LOCATION_ALIAS
    )]
)
class GeolocationPlugin : Plugin() {

    private lateinit var controller: OSGLOCController
    private lateinit var coroutineScope: CoroutineScope
    private val watchingCalls: MutableMap<String, PluginCall> = mutableMapOf()

    companion object {
        const val LOCATION_ALIAS: String = "location"
        const val COARSE_LOCATION_ALIAS: String = "coarseLocation"
    }

    override fun load() {
        super.load()

        coroutineScope = CoroutineScope(Dispatchers.Main)
        val activityLauncher = activity.registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            CoroutineScope(Dispatchers.Main).launch {
                controller.onResolvableExceptionResult(result.resultCode)
            }
        }

        this.controller = OSGLOCController(
            LocationServices.getFusedLocationProviderClient(context),
            activityLauncher
        )

    }

    override fun handleOnDestroy() {
        super.handleOnDestroy()
        coroutineScope.cancel()
    }

    @PluginMethod
    override fun checkPermissions(call: PluginCall) {
        if (controller.areLocationServicesEnabled(context)) {
            super.checkPermissions(call)
        } else {
            call.sendError(OSGeolocationErrors.LOCATION_DISABLED)
        }
    }

    @PluginMethod
    override fun requestPermissions(call: PluginCall) {
        if (controller.areLocationServicesEnabled(context)) {
            super.requestPermissions(call)
        } else {
            call.sendError(OSGeolocationErrors.LOCATION_DISABLED)
        }
    }

    /**
     * Checks location permission state, requesting them if necessary.
     * If not, calls getPosition to get the device's position
     * @param call the plugin call
     */
    @PluginMethod
    fun getCurrentPosition(call: PluginCall) {
        val alias = getAlias(call)
        if (getPermissionState(alias) != PermissionState.GRANTED) {
            requestPermissionForAlias(alias, call, "completeCurrentPosition")
        } else {
            getPosition(call)
        }
    }

    /**
     * Completes the getCurrentPosition plugin call after a permission request
     * @see .getCurrentPosition
     * @param call the plugin call
     */
    @PermissionCallback
    private fun completeCurrentPosition(call: PluginCall) {
        if (getPermissionState(COARSE_LOCATION_ALIAS) == PermissionState.GRANTED) {
            getPosition(call)
        } else {
            call.sendError(OSGeolocationErrors.LOCATION_PERMISSIONS_DENIED)
        }
    }

    /**
     * Checks location permission state, requesting them if necessary.
     * If not, calls startWatch to start getting location updates
     * @param call the plugin call
     */
    @PluginMethod(returnType = PluginMethod.RETURN_CALLBACK)
    fun watchPosition(call: PluginCall) {
        val alias = getAlias(call)
        if (getPermissionState(alias) != PermissionState.GRANTED) {
            requestPermissionForAlias(alias, call, "completeWatchPosition")
        } else {
            startWatch(call)
        }
    }

    /**
     * Completes the watchPosition plugin call after a permission request
     * @see .startWatch
     * @param call the plugin call
     */
    @PermissionCallback
    private fun completeWatchPosition(call: PluginCall) {
        if (getPermissionState(COARSE_LOCATION_ALIAS) == PermissionState.GRANTED) {
            startWatch(call)
        } else {
            call.sendError(OSGeolocationErrors.LOCATION_PERMISSIONS_DENIED)
        }
    }

    /**
     * Clears the watch, removing location updates.
     * @param call the plugin call
     */
    @PluginMethod
    fun clearWatch(call: PluginCall) {
        val id = call.getString("id")
        if (id.isNullOrBlank()) {
            call.sendError(OSGeolocationErrors.WATCH_ID_NOT_PROVIDED)
        } else {
            watchingCalls.remove(id)?.release(bridge)
            val watchCleared = controller.clearWatch(id)
            if (watchCleared) {
                call.sendSuccess()
            } else {
                call.sendError(OSGeolocationErrors.WATCH_ID_NOT_FOUND)
            }
        }
    }

    /**
     * Gets the appropriate permission alias
     * @param call the plugin call
     * @return String with correct alias
     */
    private fun getAlias(call: PluginCall): String {
        var alias = LOCATION_ALIAS
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val enableHighAccuracy = call.getBoolean("enableHighAccuracy") ?: false
            if (!enableHighAccuracy) {
                alias = COARSE_LOCATION_ALIAS
            }
        }
        return alias
    }

    /**
     * Gets the current position
     * @param call the plugin call
     */
    private fun getPosition(call: PluginCall) {
        coroutineScope.launch {
            val locationOptions = createOptions(call)

            // call getCurrentPosition method from controller
            val locationResult = controller.getCurrentPosition(activity, locationOptions)

            locationResult
                .onSuccess { location ->
                    call.sendSuccess(getJSObjectForLocation(location))
                }
                .onFailure { exception ->
                    onLocationError(exception, call)
                }
        }
    }

    /**
     * Starts watching the device's location by requesting location updates
     * @param call the plugin call
     */
    private fun startWatch(call: PluginCall) {
        coroutineScope.launch {
            val watchId = call.callbackId

            val locationOptions = createOptions(call)

            // call addWatch method from controller
            controller.addWatch(activity, locationOptions, watchId).collect { result ->
                result.onSuccess { locationList ->
                    locationList.forEach { locationResult ->
                        call.sendSuccess(
                            result = getJSObjectForLocation(locationResult), 
                            keepCallback = true)
                    }
                }
                result.onFailure { exception ->
                    onLocationError(exception, call)
                }
            }
        }
        watchingCalls[call.callbackId] = call
    }

    /**
     * Helper function to convert OSGLOCLocationResult object into the format accepted by the Capacitor bridge
     * @param locationResult OSGLOCLocationResult object with the location to convert
     * @return JSObject with converted JSON object
     */
    private fun getJSObjectForLocation(locationResult: OSGLOCLocationResult): JSObject {
        val coords = JSObject().apply {
            put("latitude", locationResult.latitude)
            put("longitude", locationResult.longitude)
            put("accuracy", locationResult.accuracy)
            put("altitude", locationResult.altitude)
            locationResult.altitudeAccuracy?.let { put("altitudeAccuracy", it) }
            put("speed", locationResult.speed)
            put("heading", locationResult.heading)
        }
        return JSObject().apply {
            put("timestamp", locationResult.timestamp)
            put("coords", coords)
        }
    }

    /**
     * Helper function to handle error cases
     * @param exception Throwable to handle as an error
     * @param call the plugin call
     */
    private fun onLocationError(exception: Throwable?, call: PluginCall) {
        when (exception) {
            is OSGLOCException.OSGLOCRequestDeniedException -> {
                call.sendError(OSGeolocationErrors.LOCATION_ENABLE_REQUEST_DENIED)
            }
            is OSGLOCException.OSGLOCSettingsException -> {
                call.sendError(OSGeolocationErrors.LOCATION_SETTINGS_ERROR)
            }
            is OSGLOCException.OSGLOCInvalidTimeoutException -> {
                call.sendError(OSGeolocationErrors.INVALID_TIMEOUT)
            }
            is OSGLOCException.OSGLOCGoogleServicesException -> {
                if (exception.resolvable) {
                    call.sendError(OSGeolocationErrors.GOOGLE_SERVICES_RESOLVABLE)
                } else {
                    call.sendError(OSGeolocationErrors.GOOGLE_SERVICES_ERROR)
                }
            }
            else -> {
                call.sendError(OSGeolocationErrors.GET_LOCATION_GENERAL)
            }
        }
    }

    /**
     * Extension function to return a successful plugin result
     * @param result JSOObject with the JSON content to return
     * @param keepCallback boolean to determine if callback should be kept for future calls or not
     */
    private fun PluginCall.sendSuccess(result: JSObject? = null, keepCallback: Boolean? = false) {
        this.setKeepAlive(keepCallback)
        if (result != null) {
            this.resolve(result)
        } else {
            this.resolve()
        }
    }

    /**
     * Extension function to return a unsuccessful plugin result
     * @param error error class representing the error to return, containing a code and message
     */
    private fun PluginCall.sendError(error: OSGeolocationErrors.ErrorInfo) {
        this.reject(error.message, error.code)
    }

    /**
     * Creates the location options to pass to the native controller
     * @param call the plugin call
     * @return OSGLOCLocationOptions object
     */
    private fun createOptions(call: PluginCall): OSGLOCLocationOptions {
        val timeout = call.getLong("timeout", 10000) ?: 10000
        val maximumAge = call.getLong("maximumAge", 0) ?: 0
        val enableHighAccuracy = call.getBoolean("enableHighAccuracy", false) ?: false
        val minimumUpdateInterval = call.getLong("minimumUpdateInterval", 5000) ?: 5000

        val locationOptions = OSGLOCLocationOptions(timeout, maximumAge, enableHighAccuracy, minimumUpdateInterval)

        return locationOptions
    }
}