package com.outsystems.capacitor.plugins.geolocation

sealed class OSGeolocationPermissionEvents {
    data object Granted: OSGeolocationPermissionEvents()
    data object NotGranted: OSGeolocationPermissionEvents()
}