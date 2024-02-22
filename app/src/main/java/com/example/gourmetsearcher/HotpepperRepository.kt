package com.example.gourmetsearcher

import retrofit2.Response
import javax.inject.Inject

class HotPepperRepository @Inject constructor
    (private val service: HotPepperNetworkDataSource){
    private val key = BuildConfig.API_KEY
    private val format = "json"

    fun searchHotPepperRepository(searchTerms: SearchTerms): Response<HotPepperResponse>? {
        return try {
            service.getRestaurantDatum(
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