package com.example.gourmetsearcher.model.api

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

/**
 * ホットペッパーグルメAPIのレスポンスデータクラス
 * @param results レストラン情報
 */
@Parcelize
data class RestaurantList(
    @Json(name = "results")
    val results: Results,
) : Parcelable

/**
 * レストラン情報を保持するデータクラス
 * @param shops レストラン情報
 */
@Parcelize
data class Results(
    @Json(name = "shop")
    val shops: List<Shops>,
) : Parcelable

/**
 * レストラン情報を保持するデータクラス
 * @param id レストランID
 * @param name レストラン名
 * @param address 住所
 * @param station 最寄り駅
 * @param largeArea 大エリア
 * @param smallArea 小エリア
 * @param genre ジャンル
 * @param budget 予算
 * @param access アクセス
 * @param url URL
 * @param photo 写真
 * @param open 営業時間（開店）
 * @param close 営業時間（閉店）
 */
@Parcelize
data class Shops(
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

/**
 * 大エリア情報を保持するデータクラス
 * @param name 大エリア名
 */
@Parcelize
data class LargeAreaData(
    @Json(name = "name")
    val name: String,
) : Parcelable

/**
 * 小エリア情報を保持するデータクラス
 * @param name 小エリア名
 */
@Parcelize
data class SmallAreaData(
    @Json(name = "name")
    val name: String,
) : Parcelable

/**
 * ジャンル情報を保持するデータクラス
 * @param name ジャンル名
 */
@Parcelize
data class GenreData(
    @Json(name = "name")
    val name: String,
) : Parcelable

/**
 * 予算情報を保持するデータクラス
 * @param name 予算
 */
@Parcelize
data class BudgetData(
    @Json(name = "name")
    val name: String,
) : Parcelable

/**
 * 店舗URL情報を保持するデータクラス
 * @param pc PCサイトURL
 */
@Parcelize
data class Urls(
    @Json(name = "pc")
    val pc: String,
) : Parcelable

/**
 * 写真情報を保持するデータクラス
 * @param pc
 */
@Parcelize
data class PhotoData(
    @Json(name = "pc")
    val pc: PCData,
) : Parcelable

/**
 * 店舗トップ写真URL情報を保持するデータクラス
 * @param l 店舗トップ写真(大）画像URL
 */
@Parcelize
data class PCData(
    @Json(name = "l")
    val l: String,
) : Parcelable
