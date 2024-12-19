package com.outsystems.plugins.osgeolocation.model

data class OSLocationResult(
    val latitude: Double,
    val longitude: Double,
    val altitude: Double,
    val accuracy: Float,
    val altitudeAccuracy: Float? = null,
    val heading: Float,
    val speed: Float,
    val timestamp: Long
)
