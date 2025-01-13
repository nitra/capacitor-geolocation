import Capacitor
import OSGeolocationLib

private enum GeolocationCallbackType {
    case location
    case watch

    var shouldKeepCallback: Bool {
        self == .watch
    }

    var shouldClearAfterSending: Bool {
        self == .location
    }
}

private struct GeolocationCallbackGroup {
    let ids: [CAPPluginCall]
    let type: GeolocationCallbackType
}

final class GeolocationCallbackManager {
    private(set) var locationCallbacks: [CAPPluginCall]
    private(set) var watchCallbacks: [String: CAPPluginCall]
    private let capacitorBridge: CAPBridgeProtocol?

    private var allCallbackGroups: [GeolocationCallbackGroup] {
        [
            .init(ids: locationCallbacks, type: .location),
            .init(ids: Array(watchCallbacks.values), type: .watch)
        ]
    }

    init(capacitorBridge: CAPBridgeProtocol?) {
        self.capacitorBridge = capacitorBridge
        self.locationCallbacks = []
        self.watchCallbacks = [:]
    }

    func addLocationCallback(capacitorCall call: CAPPluginCall) {
        capacitorBridge?.saveCall(call)
        locationCallbacks.append(call)
    }

    func addWatchCallback(_ watchId: String, capacitorCall call: CAPPluginCall) {
        capacitorBridge?.saveCall(call)
        watchCallbacks[watchId] = call
    }

    func clearWatchCallbackIfExists(_ watchId: String) -> Bool {
        guard let callbackToRemove = watchCallbacks.removeValue(forKey: watchId) else {
            return false
        }
        capacitorBridge?.releaseCall(callbackToRemove)
        return true
    }

    func clearLocationCallbacks() {
        locationCallbacks.forEach {
            capacitorBridge?.releaseCall($0)
        }
        locationCallbacks.removeAll()
    }

    func sendSuccess(_ call: CAPPluginCall) {
        call.resolve()
    }

    func sendSuccess(_ call: CAPPluginCall, with data: PluginCallResultData) {
        call.resolve(data)
    }

    func sendSuccess(with position: OSGLOCPositionModel) {
        createPluginResult(status: .success(position.toJSObject()))
    }

    func sendError(_ error: GeolocationError) {
        createPluginResult(status: .error(error.toCodeMessagePair()))
    }
}

private enum CallResultStatus {
    typealias SuccessModel = JSObject
    typealias ErrorModel = (code: String, message: String)

    case success(_ data: SuccessModel)
    case error(_ codeAndMessage: ErrorModel)
}

private extension GeolocationCallbackManager {
    func createPluginResult(status: CallResultStatus) {
        allCallbackGroups.forEach {
            send(status, to: $0)
        }
    }

    func send(_ callResultStatus: CallResultStatus, to group: GeolocationCallbackGroup) {
        group.ids.forEach { call in
            call.keepAlive = group.type.shouldKeepCallback
            switch callResultStatus {
            case .success(let data):
                call.resolve(data)
            case .error(let error):
                call.reject(error.message, error.code)
            }
        }

        if group.type.shouldClearAfterSending {
            clearCallbacks(for: group.type)
        }
    }

    func clearCallbacks(for type: GeolocationCallbackType) {
        if case .location = type {
            clearLocationCallbacks()
        }
    }
}
