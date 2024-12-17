package com.outsystems.plugins.osgeolocation.model

data class OSLocationOptions(
    val timeout: Long = 5000,
    val maximumAge: Long = 3000,
    val enableHighAccuracy: Boolean = true
)
