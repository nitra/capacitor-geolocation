package com.outsystems.capacitor.plugins.geolocation

import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin

@CapacitorPlugin(name = "GeolocationPlugin")
class GeolocationPlugin : Plugin() {

    @PluginMethod
    fun getCurrentPosition(call: PluginCall) {
        TODO("Not yet implemented")
    }

    @PluginMethod
    fun watchPosition(call: PluginCall) {
        TODO("Not yet implemented")
    }

    @PluginMethod
    fun clearWatch(call: PluginCall) {
        TODO("Not yet implemented")
    }

}