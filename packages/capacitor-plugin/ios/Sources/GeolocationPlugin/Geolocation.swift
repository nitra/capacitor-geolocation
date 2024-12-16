import Foundation
import Capacitor
import OSGeolocationLib

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(GeolocationPlugin)
public class GeolocationPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "GeolocationPlugin"
    public let jsName = "GeolocationPlugin"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "test", returnType: CAPPluginReturnPromise)
    ]
    private let implementationLib = PingDemoLib()

    @objc func ping(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementationLib.ping(value)
        ])
    }
}
