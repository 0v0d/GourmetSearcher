package com.example.gourmetsearcher.model.data

import kotlinx.serialization.Serializable

/**
 * 現在地の緯度経度を保持するデータクラス
 * @param lat 緯度
 * @param lng 経度
 */
@Serializable
data class CurrentLocation(
    val lat: Double,
    val lng: Double,
)
