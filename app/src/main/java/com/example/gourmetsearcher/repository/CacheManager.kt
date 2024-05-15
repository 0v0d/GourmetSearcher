package com.example.gourmetsearcher.repository

import android.util.LruCache
import com.example.gourmetsearcher.model.api.HotPepperResponse
import com.example.gourmetsearcher.model.data.SearchTerms
import retrofit2.Response


/**
 * キャッシュマネージャ
 * @param cacheSize キャッシュサイズ
 */
class CacheManager(cacheSize: Int = 5) {
    /** キャッシュ */
    private val cache = LruCache<SearchTerms, Response<HotPepperResponse>?>(cacheSize)

    /**
     *  キャッシュから結果を取得する
     * @param searchTerms 検索条件
     * @return レストラン情報 or null
     */
    fun get(searchTerms: SearchTerms): Response<HotPepperResponse>? {
        return cache[searchTerms]
    }

    /**
     * キャッシュに結果を保存する
     * @param searchTerms 検索条件
     * @param response レストラン情報
     */
    fun put(searchTerms: SearchTerms, response: Response<HotPepperResponse>?) {
        cache.put(searchTerms, response)
    }
}
