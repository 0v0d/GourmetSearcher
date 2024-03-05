package com.example.gourmetsearcher.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

/**
 * ホットペッパーグルメAPIのレスポンスデータクラス
 * @param results レストラン情報
 */
@Parcelize
data class HotPepperResponse(
    @Json(name = "results")
    val results: Results
) : Parcelable

/**
 * レストラン情報を保持するデータクラス
 * @param shops レストラン情報
 */
@Parcelize
data class Results(
    @Json(name = "shop")
    val shops: List<RestaurantData>
) : Parcelable