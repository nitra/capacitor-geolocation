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
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import java.util.UUID

/**
 * Cordova bridge, inherits from CordovaPlugin
 */
class OSGeolocation : CordovaPlugin() {

    private lateinit var controller: OSGeolocationController
    private val gson by lazy { Gson() }

    // for permissions
    private lateinit var flow: MutableSharedFlow<OSGeolocationPermissionEvents>
    private lateinit var coroutineScope: CoroutineScope

    companion object {
        private const val LOCATION_PERMISSIONS_REQUEST_CODE = 22332
        private const val TIMEOUT = "timeout"
        private const val MAXIMUM_AGE = "maximumAge"
        private const val ENABLE_HIGH_ACCURACY = "enableHighAccuracy"
    }

    override fun initialize(cordova: CordovaInterface, webView: CordovaWebView) {
        super.initialize(cordova, webView)

        coroutineScope = CoroutineScope(Dispatchers.Main)
        val activityLauncher = cordova.activity.registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            coroutineScope.launch {
                controller.onResolvableExceptionResult(result.resultCode)
            }
        }

        this.controller = OSGeolocationController(
            LocationServices.getFusedLocationProviderClient(cordova.context),
            activityLauncher
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

    override fun execute(
        action: String,
        args: JSONArray,
        callbackContext: CallbackContext
    ): Boolean {
        when (action) {
            "getCurrentPosition" -> {
                getCurrentPosition(args, callbackContext)
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
    private fun getCurrentPosition(args: JSONArray, callbackContext: CallbackContext) {
        val parameters: JSONObject
        try {
            parameters = args.getJSONObject(0)
        } catch (e: Exception) {
            callbackContext.sendError(OSGeolocationErrors.INVALID_INPUT)
            return
        }

        coroutineScope.launch {
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
                    // put parameters in object to send that object to getCurrentPosition
                    // the way we get the arguments may change

                    val locationOptions = OSLocationOptions(
                        parameters.getLong(TIMEOUT),
                        parameters.getLong(MAXIMUM_AGE),
                        parameters.getBoolean(ENABLE_HIGH_ACCURACY))

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
                                callbackContext.sendError(OSGeolocationErrors.LOCATION_SETTINGS_ERROR)
                            }
                            is OSLocationException.OSLocationInvalidTimeoutException -> {
                                callbackContext.sendError(OSGeolocationErrors.INVALID_TIMEOUT)
                            }
                            is OSLocationException.OSLocationGoogleServicesException -> {
                                if (exception.resolvable) {
                                    callbackContext.sendError(OSGeolocationErrors.GOOGLE_SERVICES_RESOLVABLE)
                                } else {
                                    callbackContext.sendError(OSGeolocationErrors.GOOGLE_SERVICES_ERROR)
                                }
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
        val parameters: JSONObject
        try {
            parameters = args.getJSONObject(0)
        } catch (e: Exception) {
            callbackContext.sendError(OSGeolocationErrors.INVALID_INPUT)
            return
        }

        val watchId = UUID.randomUUID().toString()
        callbackContext.sendSuccess(
            result = JSONObject("{\"id\": \"$watchId\"}"),
            keepCallback = true
        )

        coroutineScope.launch {
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
                    val locationOptions = OSLocationOptions(
                        maximumAge = args.getLong(2),
                        enableHighAccuracy = args.getBoolean(1),
                    )

                    controller.addWatch(cordova.activity, locationOptions, watchId).collect { result ->

                        result.onSuccess { locationList ->
                            locationList.forEach { locationResult ->
                                callbackContext.sendSuccess(
                                    result = JSONObject(gson.toJson(locationResult)),
                                    keepCallback = true
                                )
                            }
                        }
                        result.onFailure { exception ->
                            when (exception) {
                                is OSLocationException.OSLocationRequestDeniedException -> {
                                    callbackContext.sendError(OSGeolocationErrors.LOCATION_ENABLE_REQUEST_DENIED)
                                }
                                is OSLocationException.OSLocationSettingsException -> {
                                    callbackContext.sendError(OSGeolocationErrors.LOCATION_SETTINGS_ERROR)
                                }
                                is OSLocationException.OSLocationInvalidTimeoutException -> {
                                    callbackContext.sendError(OSGeolocationErrors.INVALID_TIMEOUT)
                                }
                                is OSLocationException.OSLocationGoogleServicesException -> {
                                    if (exception.resolvable) {
                                        callbackContext.sendError(OSGeolocationErrors.GOOGLE_SERVICES_RESOLVABLE)
                                    } else {
                                        callbackContext.sendError(OSGeolocationErrors.GOOGLE_SERVICES_ERROR)
                                    }
                                }
                                else -> {
                                    callbackContext.sendError(OSGeolocationErrors.GET_LOCATION_GENERAL)
                                }
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
     * Calls the addWatch method of OSGeolocationController to stop watching the device's geolocation
     * @param args JSONArray that contains the parameters to parse (e.g. timeout)
     * @param callbackContext CallbackContext the method should return to
     */
    private fun clearWatch(args: JSONArray, callbackContext: CallbackContext) {
        val id = args.optString(0)
        if (id.isNullOrBlank()) {
            callbackContext.sendError(OSGeolocationErrors.WATCH_ID_NOT_PROVIDED)
            return
        }
        val watchCleared = controller.clearWatch(id)
        if (watchCleared) {
            callbackContext.sendSuccess()
        } else {
            callbackContext.sendError(OSGeolocationErrors.WATCH_ID_NOT_FOUND)
        }
    }

    /**
     * Extension function to return a successful plugin result
     * @param result JSONObject with the JSON content to return, or null if there's no json data
     * @param keepCallback whether the callback should be kept or not. By default, false
     */
    private fun CallbackContext.sendSuccess(result: JSONObject? = null, keepCallback: Boolean = false) {
        val pluginResult = if (result != null) {
            PluginResult(PluginResult.Status.OK, result)
        } else {
            PluginResult(PluginResult.Status.OK)
        }
        pluginResult.keepCallback = keepCallback
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
            coroutineScope.launch {
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