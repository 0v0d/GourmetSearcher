package com.example.gourmetsearcher.source

import com.example.gourmetsearcher.model.HotPepperResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/** ホットペッパーグルメAPIのインターフェース */
interface HotPepperNetworkDataSource {
    /**
     * ホットペッパーグルメAPIを利用して、レストラン情報を取得
     * @param key APIキー
     * @param keyword 検索キーワード
     * @param lat 緯度
     * @param lng 経度
     * @param range 検索範囲
     * @param format レスポンスフォーマット
     * @return Call<HotPepperResponse> レストラン情報
     */
    @GET("gourmet/v1/")
    fun getRestaurantDatum(
        @Query("key") key: String,
        @Query("keyword") keyword: String,
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("range") range: Int,
        @Query("format") format: String,
    ): Call<HotPepperResponse>
}