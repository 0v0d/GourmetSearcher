package com.example.gourmetsearcher.repository

import android.util.LruCache
import com.example.gourmetsearcher.model.api.HotPepperResponse
import com.example.gourmetsearcher.model.data.SearchTerms
import retrofit2.Response


class CacheManager(cacheSize: Int = 5) {
    private val cache = LruCache<SearchTerms, Response<HotPepperResponse>?>(cacheSize)

    fun get(searchTerms: SearchTerms): Response<HotPepperResponse>? {
        return cache[searchTerms]
    }

    fun put(searchTerms: SearchTerms, response: Response<HotPepperResponse>?) {
        cache.put(searchTerms, response)
    }
}
