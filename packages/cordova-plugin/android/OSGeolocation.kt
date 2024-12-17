package com.outsystems.cordova.plugins.osgeolocation

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.apache.cordova.CallbackContext
import org.apache.cordova.CordovaInterface
import org.apache.cordova.CordovaPlugin
import org.apache.cordova.CordovaWebView
import org.apache.cordova.PluginResult
import org.json.JSONArray
import org.json.JSONObject
import com.outsystems.plugins.osgeolocation.controller.OSGeolocationController
import com.google.gson.Gson
import com.outsystems.plugins.osgeolocation.model.OSLocationOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Cordova bridge, inherits from CordovaPlugin
 */
class OSGeolocation : CordovaPlugin() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var controller: OSGeolocationController
    private val gson by lazy { Gson() }

    override fun initialize(cordova: CordovaInterface, webView: CordovaWebView) {
        super.initialize(cordova, webView)
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(cordova.context)
        this.controller = OSGeolocationController(fusedLocationClient)
    }

    override fun execute(
        action: String,
        args: JSONArray,
        callbackContext: CallbackContext
    ): Boolean {

        when (action) {
            "getLocation" -> {
                getLocation(args, callbackContext)
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
     * Calls the getLocation method of OSGeolocationController to get the device's geolocation
     * @param args JSONArray that contains the parameters to parse (e.g. timeout)
     * @param callbackContext CallbackContext the method should return to
     */
    private fun getLocation(args: JSONArray, callbackContext: CallbackContext) {

        // validate parameters in args
        // put parameters in object to send that object to getLocation

        val locationOptions = OSLocationOptions(args.getInt(2).toLong(), args.getInt(1).toLong(), args.getBoolean(0))

        // call getLocation method from controller

        CoroutineScope(Dispatchers.Main).launch {
            val locationResult = controller.getLocation(locationOptions)

            if (locationResult.isSuccess) {
                sendSuccess(callbackContext, JSONObject(gson.toJson(locationResult.getOrNull())))
            } else {
                // handle error accordingly
                val exception = locationResult.exceptionOrNull()
                val error = when (exception) {
                    is Exception -> {
                        Errors.GET_LOCATION_GENERAL
                    }
                    else -> {
                        Errors.GET_LOCATION_GENERAL
                    }
                }
                sendError(callbackContext, error)
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
     * Helper method to return a successful plugin result
     * @param callbackContext CallbackContext the method should return to
     * @param result JSONObject with the JSON content to return
     */
    private fun sendSuccess(callbackContext: CallbackContext, result: JSONObject) {
        val pluginResult = PluginResult(PluginResult.Status.OK, result)
        pluginResult.keepCallback = true
        callbackContext.sendPluginResult(pluginResult)
    }

    /**
     * Helper method to return a unsuccessful plugin result
     * @param callbackContext CallbackContext the method should return to
     * @param error error class representing the error to return, containing a code and message
     */
    private fun sendError(callbackContext: CallbackContext, error: Errors.ErrorInfo) {
        val pluginResult = PluginResult(
            PluginResult.Status.ERROR,
            JSONObject().apply {
                put("code", error.code)
                put("message", error.message)
            }
        )
        callbackContext.sendPluginResult(pluginResult)
    }

}

/**
 * Object with plugin errors
 */
object Errors {
    private fun formatErrorCode(number: Int): String {
        return "OS-PLUG-GEO-" + number.toString().padStart(4, '0')
    }

    data class ErrorInfo(
        val code: String,
        val message: String
    )

    val INVALID_INPUT = ErrorInfo(
        code = formatErrorCode(1),
        message = "The input parameters aren't valid."
    )

    val GET_LOCATION_TIMEOUT = ErrorInfo(
        code = formatErrorCode(2),
        message = "Could not obtain location in time. Try with a higher timeout."
    )

    val GET_LOCATION_GENERAL = ErrorInfo(
        code = formatErrorCode(3),
        message = "Could not obtain location in time. Try with a higher timeout."
    )
}