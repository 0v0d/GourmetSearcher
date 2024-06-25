package com.example.gourmetsearcher.model.data

import java.io.Serializable

/**
 * 現在地の緯度経度を保持するデータクラス
 * @param lat 緯度
 * @param lng 経度
 */
@kotlinx.serialization.Serializable
data class CurrentLocation(
    val lat: Double,
    val lng: Double,
) : Serializable {
    private companion object {
        @Suppress("ConstPropertyName")
        private const val serialVersionUID = 1L
    }
}
