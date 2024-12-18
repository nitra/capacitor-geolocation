package com.outsystems.cordova.plugins.osgeolocation

sealed class OSGeolocationPermissionEvents {
    data object Granted: OSGeolocationPermissionEvents()
    data object NotGranted: OSGeolocationPermissionEvents()
}