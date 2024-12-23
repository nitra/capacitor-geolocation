package com.outsystems.plugins.osgeolocation.controller

import android.os.Build

internal object OSGeolocationBuildConfig {
    fun getAndroidSdkVersionCode(): Int = Build.VERSION.SDK_INT
}