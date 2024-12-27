package com.outsystems.plugins.osgeolocation.model

/**
 * Data class representing the options passed to getCurrentPosition and watchPosition
 */
data class OSGLOCLocationOptions(
    val timeout: Long = 5000,
    val maximumAge: Long = 3000,
    val enableHighAccuracy: Boolean = true,
    val minUpdateInterval: Long? = null
)
