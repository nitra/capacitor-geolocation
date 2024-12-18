package com.outsystems.capacitor.plugins.geolocation

import androidx.activity.result.contract.ActivityResultContracts
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin
import com.google.android.gms.location.LocationServices
import com.outsystems.plugins.osgeolocation.controller.OSGeolocationController
import com.outsystems.plugins.osgeolocation.model.OSLocationException
import com.outsystems.plugins.osgeolocation.model.OSLocationOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import org.apache.cordova.geolocation.OSGeolocationErrors
import org.apache.cordova.geolocation.OSGeolocationPermissionEvents
import com.google.gson.Gson

@CapacitorPlugin(name = "Geolocation")
class GeolocationPlugin : Plugin() {

    private lateinit var controller: OSGeolocationController
    private val gson by lazy { Gson() }

    // for permissions
    private lateinit var flow: MutableSharedFlow<OSGeolocationPermissionEvents>

    companion object {
        private const val LOCATION_PERMISSIONS_REQUEST_CODE = 22332
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
    fun getCurrentPosition(call: PluginCall) {

        CoroutineScope(Dispatchers.IO).launch {
            val timeout = call.getLong("timeout") ?: 5000
            val maximumAge = call.getLong("maximumAge") ?: 3000
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

}