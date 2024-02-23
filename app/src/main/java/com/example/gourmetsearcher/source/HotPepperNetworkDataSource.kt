package com.example.gourmetsearcher.source

import com.example.gourmetsearcher.model.HotPepperResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HotPepperNetworkDataSource {
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