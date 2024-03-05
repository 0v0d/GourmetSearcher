package com.example.gourmetsearcher.model

import java.io.Serializable

/** 現在地の緯度経度を保持するデータクラス */
data class CurrentLocation(
    val lat: Double,
    val lng: Double
): Serializable