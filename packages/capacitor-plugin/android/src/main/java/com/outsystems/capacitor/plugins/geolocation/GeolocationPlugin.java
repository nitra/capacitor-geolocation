package com.outsystems.capacitor.plugins.geolocation;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.outsystems.androidlibs.pingdemolib.PingDemoLib;

@CapacitorPlugin(name = "GeolocationPlugin")
public class GeolocationPlugin extends Plugin {

    private final PingDemoLib implementationLib = new PingDemoLib();

    @PluginMethod
    public void ping(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementationLib.ping(value != null ? value : ""));
        call.resolve(ret);
    }
}
