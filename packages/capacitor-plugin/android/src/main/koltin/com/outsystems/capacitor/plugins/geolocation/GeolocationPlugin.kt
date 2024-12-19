package com.outsystems.capacitor.plugins.geolocation

import android.Manifest
import android.location.Location
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
import com.google.gson.Gson
import com.outsystems.plugins.osgeolocation.controller.OSGeolocationController
import com.outsystems.plugins.osgeolocation.model.OSLocationException
import com.outsystems.plugins.osgeolocation.model.OSLocationOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import org.apache.cordova.geolocation.OSGeolocationErrors
import org.apache.cordova.geolocation.OSGeolocationPermissionEvents

@CapacitorPlugin(
    name = "Geolocation",
    permissions = [Permission(
        strings = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION],
        alias = GeolocationPlugin.LOCATION_ALIAS
    ), Permission(
        strings = [Manifest.permission.ACCESS_COARSE_LOCATION],
        alias = GeolocationPlugin.COARSE_LOCATION_ALIAS
    )]
)
class GeolocationPlugin : Plugin() {

    private lateinit var controller: OSGeolocationController
    private val gson by lazy { Gson() }

    // for permissions
    private lateinit var flow: MutableSharedFlow<OSGeolocationPermissionEvents>

    companion object {
        private const val LOCATION_PERMISSIONS_REQUEST_CODE = 22332
        const val LOCATION_ALIAS: String = "location"
        const val COARSE_LOCATION_ALIAS: String = "coarseLocation"
    }

    override fun load() {
        super.load()

        val activityLauncher = activity.registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            CoroutineScope(Dispatchers.Main).launch {
                controller.onResolvableExceptionResult(result.resultCode)
            }
        }

        this.controller = OSGeolocationController(
            LocationServices.getFusedLocationProviderClient(context),
            activityLauncher
        )

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
        if (getPermissionState(GeolocationPluginOld.COARSE_LOCATION) == PermissionState.GRANTED) {
            getPosition(call)
        } else {
            call.sendError(OSGeolocationErrors.LOCATION_PERMISSIONS_DENIED)
        }
    }

    @PluginMethod
    fun watchPosition(call: PluginCall) {
        TODO("Not yet implemented")
    }

    @PluginMethod
    fun clearWatch(call: PluginCall) {
        TODO("Not yet implemented")
    }

    /**
     * Extension function to return a successful plugin result
     * @param result JSOObject with the JSON content to return
     */
    private fun PluginCall.sendSuccess(result: JSObject) {
        this.resolve(result)
    }

    /**
     * Extension function to return a unsuccessful plugin result
     * @param error error class representing the error to return, containing a code and message
     */
    private fun PluginCall.sendError(error: OSGeolocationErrors.ErrorInfo) {
        this.reject(error.message, error.code)
    }

    /**
     * Gets the appropriate permission alias
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
     */
    private fun getPosition(call: PluginCall) {
        CoroutineScope(Dispatchers.IO).launch {
            val timeout = call.getLong("timeout") ?: 10000
            val maximumAge = call.getLong("maximumAge") ?: 0
            val enableHighAccuracy = call.getBoolean("enableHighAccuracy") ?: false

            // validate parameters in args
            // put parameters in object to send that object to getLocation
            // the way we get the arguments may change
            val locationOptions = OSLocationOptions(timeout, maximumAge, enableHighAccuracy)
            // call getLocation method from controller

            val locationResult = controller.getLocation(activity, locationOptions)

            if (locationResult.isSuccess) {
                call.sendSuccess(JSObject(gson.toJson(locationResult.getOrNull())))
            } else {
                // handle error accordingly
                val exception = locationResult.exceptionOrNull()
                when (exception) {
                    is OSLocationException.OSLocationRequestDeniedException -> {
                        call.sendError(OSGeolocationErrors.LOCATION_ENABLE_REQUEST_DENIED)
                    }
                    is OSLocationException.OSLocationSettingsException -> {
                        call.sendError(OSGeolocationErrors.LOCATION_ENABLE_REQUEST_DENIED)
                    }
                    is OSLocationException.OSLocationInvalidTimeoutException -> {
                        call.sendError(OSGeolocationErrors.LOCATION_ENABLE_REQUEST_DENIED)
                    }
                    is OSLocationException.OSLocationGoogleServicesException -> {
                        call.sendError(OSGeolocationErrors.LOCATION_ENABLE_REQUEST_DENIED)
                    }
                    is NullPointerException -> {
                        call.sendError(OSGeolocationErrors.LOCATION_ENABLE_REQUEST_DENIED)
                    }
                    is Exception -> {
                        call.sendError(OSGeolocationErrors.LOCATION_ENABLE_REQUEST_DENIED)
                    }
                    else -> {
                        call.sendError(OSGeolocationErrors.LOCATION_ENABLE_REQUEST_DENIED)
                    }
                }
            }
        }

    }

}