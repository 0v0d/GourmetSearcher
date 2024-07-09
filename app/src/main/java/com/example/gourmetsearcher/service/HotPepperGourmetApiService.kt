package com.example.gourmetsearcher.service

import com.example.gourmetsearcher.model.api.RestaurantList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/** ホットペッパーサービス */
interface HotPepperGourmetApiService {
    /**
     * ホットペッパーグルメAPIを利用して、レストラン情報を検索
     * @param apiKey APIキー
     * @param keyword 検索キーワード
     * @param lat 緯度
     * @param lng 経度
     * @param range 検索範囲
     * @param responseFormat レスポンスフォーマット
     * @return レストラン情報のリスト
     */
    @GET("gourmet/v1/")
    suspend fun searchRestaurants(
        @Query("key") apiKey: String,
        @Query("keyword") keyword: String,
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("range") range: Int,
        @Query("format") responseFormat: String,
    ): Response<RestaurantList>
}
