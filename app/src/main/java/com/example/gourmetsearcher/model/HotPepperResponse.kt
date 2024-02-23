package com.example.gourmetsearcher.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class HotPepperResponse(
    @Json(name = "results")
    val results: Results
) : Parcelable

@Parcelize
data class Results(
    @Json(name = "shop")
    val shops: List<RestaurantData>
) : Parcelable