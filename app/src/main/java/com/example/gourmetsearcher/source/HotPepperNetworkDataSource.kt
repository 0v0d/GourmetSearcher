package com.example.gourmetsearcher.source

import com.example.gourmetsearcher.model.api.HotPepperResponse
import com.example.gourmetsearcher.model.data.RestaurantQueryParams
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

/** ホットペッパーグルメAPIのインターフェース */
interface HotPepperNetworkDataSource {
    /**
     * ホットペッパーグルメAPIを利用して、レストラン情報を取得
     * @param params リクエストパラメータ
     * @return レストラン情報
     */
    @GET("gourmet/v1/")
    suspend fun getRestaurantDatum(
        @QueryMap params: Map<String, String>,
    ): Response<HotPepperResponse>

    /**
     *  ホットペッパーグルメAPIを利用して、レストラン情報を取得
     *  @param queryParams RestaurantQueryParams
     */
    suspend fun getRestaurantDataWithObject(queryParams: RestaurantQueryParams): Response<HotPepperResponse> {
        val params =
            mapOf(
                "key" to queryParams.key,
                "keyword" to queryParams.searchTerms.keyword,
                "lat" to queryParams.searchTerms.location.lat.toString(),
                "lng" to queryParams.searchTerms.location.lng.toString(),
                "range" to queryParams.searchTerms.range.toString(),
                "format" to queryParams.format,
            )
        return getRestaurantDatum(params)
    }
}
