package com.example.gourmetsearcher.model

import java.io.Serializable

data class CurrentLocation(
    val lat: Double,
    val lng: Double
): Serializable