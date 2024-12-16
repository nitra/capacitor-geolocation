package com.outsystems.cordova.plugins.geolocation

import com.outsystems.plugins.osgeolocation.OSGeolocation;
import org.apache.cordova.CallbackContext
import org.apache.cordova.CordovaInterface
import org.apache.cordova.CordovaPlugin
import org.apache.cordova.CordovaWebView
import org.apache.cordova.PluginResult
import org.json.JSONArray
import org.json.JSONObject

class OSGeolocation: CordovaPlugin() {

    private val implementationLib = OSGeolocation()

    override fun initialize(cordova: CordovaInterface, webView: CordovaWebView) {
        super.initialize(cordova, webView)
    }

    override fun execute(
        action: String,
        args: JSONArray,
        callbackContext: CallbackContext
    ): Boolean {
        when(action) {
            "ping" -> {
                ping(args, callbackContext)
            }
        }
        return true
    }

    /**
     * Calls the openExternalBrowser method of OSIABEngine to open the url in the device's browser app
     * @param args JSONArray that contains the parameters to parse (e.g. url to open)
     * @param callbackContext CallbackContext the method should return to
     */
    private fun ping(args: JSONArray, callbackContext: CallbackContext) {
        val value: String?

        try {
            val argumentsDictionary = args.getJSONObject(0)
            value = argumentsDictionary.getString("value")
            if(value.isNullOrEmpty()) throw IllegalArgumentException()
        }
        catch (e: Exception) {
            sendError(callbackContext, Errors.INVALID_INPUT)
            return
        }

        sendSuccess(callbackContext, JSONObject().apply {
            put("value", implementationLib.ping(value))
        })
    }

    private fun sendSuccess(callbackContext: CallbackContext, passback: JSONObject) {
        val pluginResult = PluginResult(PluginResult.Status.OK, passback)
        pluginResult.keepCallback = true
        callbackContext.sendPluginResult(pluginResult)
    }

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

object Errors {
    private fun formatErrorCode(number: Int): String {
        return "OS-PLUG-GEO-" + number.toString().padStart(4, '0')
    }

    data class ErrorInfo(
        val code: String,
        val message: String
    )

    val INVALID_INPUT = ErrorInfo(
        code = formatErrorCode(5),
        message = "The input parameters aren't valid."
    )
}