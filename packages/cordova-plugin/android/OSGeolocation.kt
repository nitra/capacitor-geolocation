package com.outsystems.cordova.plugins.osgeolocation

import org.apache.cordova.CallbackContext
import org.apache.cordova.CordovaInterface
import org.apache.cordova.CordovaPlugin
import org.apache.cordova.CordovaWebView
import org.apache.cordova.PluginResult
import org.json.JSONArray
import org.json.JSONObject

class OSGeolocation : CordovaPlugin() {

    override fun initialize(cordova: CordovaInterface?, webView: CordovaWebView?) {
        super.initialize(cordova, webView)
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

    private fun getLocation(args: JSONArray, callbackContext: CallbackContext) {
        TODO("Not yet implemented")
    }

    private fun addWatch(args: JSONArray, callbackContext: CallbackContext) {
        TODO("Not yet implemented")
    }

    private fun clearWatch(args: JSONArray, callbackContext: CallbackContext) {
        TODO("Not yet implemented")
    }

    private fun sendSuccess(callbackContext: CallbackContext, result: JSONObject) {
        val pluginResult = PluginResult(PluginResult.Status.OK, result)
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
        code = formatErrorCode(1),
        message = "The input parameters aren't valid."
    )
}