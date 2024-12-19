package com.outsystems.plugins.osgeolocation

import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import com.outsystems.plugins.osgeolocation.controller.OSGeolocationController
import com.outsystems.plugins.osgeolocation.model.OSLocationOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apache.cordova.CallbackContext
import org.apache.cordova.CordovaInterface
import org.apache.cordova.CordovaPlugin
import org.apache.cordova.CordovaWebView
import org.apache.cordova.PermissionHelper
import org.apache.cordova.PluginResult
import org.json.JSONArray
import org.json.JSONObject
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import com.outsystems.plugins.osgeolocation.model.OSLocationException
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * Cordova bridge, inherits from CordovaPlugin
 */
class OSGeolocation : CordovaPlugin() {

    private lateinit var controller: OSGeolocationController
    private val gson by lazy { Gson() }

    // for permissions
    private lateinit var flow: MutableSharedFlow<OSGeolocationPermissionEvents>

    companion object {
        private const val LOCATION_PERMISSIONS_REQUEST_CODE = 22332
        private const val TIMEOUT = "timeout"
        private const val MAXIMUM_AGE = "maximumAge"
        private const val ENABLE_HIGH_ACCURACY = "enableHighAccuracy"
    }

    override fun initialize(cordova: CordovaInterface, webView: CordovaWebView) {
        super.initialize(cordova, webView)

        val activityLauncher = cordova.activity.registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            CoroutineScope(Dispatchers.Main).launch {
                controller.onResolvableExceptionResult(result.resultCode)
            }
        }

        this.controller = OSGeolocationController(
            LocationServices.getFusedLocationProviderClient(cordova.context),
            activityLauncher
        )

    }

    override fun execute(
        action: String,
        args: JSONArray,
        callbackContext: CallbackContext
    ): Boolean {
        val parameters = args.getJSONObject(0)
        when (action) {
            "getCurrentPosition" -> {
                getCurrentPosition(parameters, callbackContext)
            }
            "addWatch" -> {
                addWatch(args, callbackContext)
            }
            "clearWatch" -> {
                clearWatch(args, callbackContext)
            }
        }
        return true
    }

    /**
     * Calls the getCurrentPosition method of OSGeolocationController to get the device's geolocation
     * @param args JSONArray that contains the parameters to parse (e.g. timeout)
     * @param callbackContext CallbackContext the method should return to
     */
    private fun getCurrentPosition(parameters: JSONObject, callbackContext: CallbackContext) {
        CoroutineScope(Dispatchers.IO).launch {
            flow = MutableSharedFlow(replay = 1)

            // first, we request permissions if necessary
            if (hasLocationPermissions()) {
                flow.emit(OSGeolocationPermissionEvents.Granted)
            } else { // request necessary permissions
                requestLocationPermissions()
            }

            // collect the flow to handle permission request result
            flow.collect { permissionEvent ->
                if (permissionEvent == OSGeolocationPermissionEvents.Granted) {
                    // validate parameters in args
                    // put parameters in object to send that object to getLocation
                    // the way we get the arguments may change

                    val locationOptions = OSLocationOptions(
                        parameters.getLong(TIMEOUT),
                        parameters.getLong(MAXIMUM_AGE),
                        parameters.getBoolean(ENABLE_HIGH_ACCURACY))

                    // call getCurrentPosition method from controller
                    val locationResult = controller.getCurrentPosition(cordova.activity, locationOptions)

                    if (locationResult.isSuccess) {
                        callbackContext.sendSuccess(JSONObject(gson.toJson(locationResult.getOrNull())))
                    } else {
                        // handle error accordingly
                        val exception = locationResult.exceptionOrNull()
                        when (exception) {
                            is OSLocationException.OSLocationRequestDeniedException -> {
                                callbackContext.sendError(OSGeolocationErrors.LOCATION_ENABLE_REQUEST_DENIED)
                            }
                            is OSLocationException.OSLocationSettingsException -> {
                                callbackContext.sendError(OSGeolocationErrors.GET_LOCATION_GENERAL)
                            }
                            is OSLocationException.OSLocationInvalidTimeoutException -> {
                                callbackContext.sendError(OSGeolocationErrors.GET_LOCATION_GENERAL)
                            }
                            is OSLocationException.OSLocationGoogleServicesException -> {
                                callbackContext.sendError(OSGeolocationErrors.GET_LOCATION_GENERAL)
                            }
                            is NullPointerException -> {
                                callbackContext.sendError(OSGeolocationErrors.GET_LOCATION_GENERAL)
                            }
                            is Exception -> {
                                callbackContext.sendError(OSGeolocationErrors.GET_LOCATION_GENERAL)
                            }
                            else -> {
                                callbackContext.sendError(OSGeolocationErrors.GET_LOCATION_GENERAL)
                            }
                        }
                    }
                } else {
                    callbackContext.sendError(OSGeolocationErrors.LOCATION_PERMISSIONS_DENIED)
                }
            }
        }
    }

    /**
     * Calls the addWatch method of OSGeolocationController to start watching the device's geolocation
     * @param args JSONArray that contains the parameters to parse (e.g. timeout)
     * @param callbackContext CallbackContext the method should return to
     */
    private fun addWatch(args: JSONArray, callbackContext: CallbackContext) {
        TODO("Not yet implemented")
    }

    /**
     * Calls the addWatch method of OSGeolocationController to stop watching the device's geolocation
     * @param args JSONArray that contains the parameters to parse (e.g. timeout)
     * @param callbackContext CallbackContext the method should return to
     */
    private fun clearWatch(args: JSONArray, callbackContext: CallbackContext) {
        TODO("Not yet implemented")
    }

    /**
     * Extension function to return a successful plugin result
     * @param result JSONObject with the JSON content to return
     */
    private fun CallbackContext.sendSuccess(result: JSONObject) {
        val pluginResult = PluginResult(PluginResult.Status.OK, result)
        pluginResult.keepCallback = true
        this.sendPluginResult(pluginResult)
    }

    /**
     * Extension function to return a unsuccessful plugin result
     * @param error error class representing the error to return, containing a code and message
     */
    private fun CallbackContext.sendError(error: OSGeolocationErrors.ErrorInfo) {
        val pluginResult = PluginResult(
            PluginResult.Status.ERROR,
            JSONObject().apply {
                put("code", error.code)
                put("message", error.message)
            }
        )
        this.sendPluginResult(pluginResult)
    }

    private fun hasLocationPermissions(): Boolean {
        for (permission in listOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            if (!PermissionHelper.hasPermission(this, permission)) {
                return false
            }
        }
        return true
    }

    private fun requestLocationPermissions() {
        PermissionHelper.requestPermissions(
            this,
            LOCATION_PERMISSIONS_REQUEST_CODE,
            listOf(
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            ).toTypedArray()
        )
    }

    override fun onRequestPermissionResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSIONS_REQUEST_CODE) {
            CoroutineScope(Dispatchers.IO).launch {
                flow.emit(
                    if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                        OSGeolocationPermissionEvents.Granted
                    } else {
                        OSGeolocationPermissionEvents.NotGranted
                    }
                )
            }
        }
    }

}