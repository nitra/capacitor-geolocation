package com.outsystems.plugins.osgeolocation

sealed class OSGeolocationPermissionEvents {
    data object Granted: OSGeolocationPermissionEvents()
    data object NotGranted: OSGeolocationPermissionEvents()
}