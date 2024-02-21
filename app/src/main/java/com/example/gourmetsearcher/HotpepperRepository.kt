package com.example.gourmetsearcher

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class HotpepperRepository {
    private val key = BuildConfig.API_KEY
    private val format = "json"
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS) // 接続のタイムアウト
        .readTimeout(30, TimeUnit.SECONDS)    // 読み取りのタイムアウト
        .writeTimeout(30, TimeUnit.SECONDS)   // 書き込みのタイムアウト
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://webservice.recruit.co.jp/hotpepper/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .build()

    private val service = retrofit.create(HotpepperInterface::class.java)

    fun getRestaurantData(
        searchTerms: SearchTerms
    ): Response<HotpepperResponse>? {
        return try {
            service.getRestaurantData(
                key,
                searchTerms.keyword,
                searchTerms.location.lat,
                searchTerms.location.lng,
                searchTerms.range,
                format
            ).execute()
        } catch (e: Exception) {
            null
        }
    }
}