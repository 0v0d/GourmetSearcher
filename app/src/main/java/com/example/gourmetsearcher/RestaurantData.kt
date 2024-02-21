package com.example.gourmetsearcher

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

// レストランデータ
@Parcelize
data class RestaurantData(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "address")
    val address: String,
    @Json(name = "station_name")
    val station: String,
    @Json(name = "large_area")
    val largeArea: LargeAreaData,
    @Json(name = "small_area")
    val smallArea: SmallAreaData,
    @Json(name = "lat")
    val latitude: Double,
    @Json(name = "lng")
    val longitude: Double,
    @Json(name = "genre")
    val genre: GenreData,
    @Json(name = "budget")
    val budget: BudgetData,
    @Json(name = "mobile_access")
    val access: String,
    @Json(name = "urls")
    val url: Urls,
    @Json(name = "photo")
    val photo: PhotoData,
    @Json(name = "open")
    val open: String,
    @Json(name = "close")
    val close: String,

    ) : Parcelable

@Parcelize
data class LargeAreaData(
    @Json(name = "name")
    val name: String,
) : Parcelable

@Parcelize
data class SmallAreaData(
    @Json(name = "name")
    val name: String,
) : Parcelable

@Parcelize
data class GenreData(
    @Json(name = "name")
    val name: String,
) : Parcelable

@Parcelize
data class BudgetData(
    @Json(name = "name")
    val name: String,
) : Parcelable

@Parcelize
data class Urls(
    @Json(name = "pc")
    val pc: String,
) : Parcelable

@Parcelize
data class PhotoData(
    @Json(name = "pc")
    val pc: PCData,
) : Parcelable

@Parcelize
data class PCData(
    @Json(name = "l")
    val l : String,
) : Parcelable