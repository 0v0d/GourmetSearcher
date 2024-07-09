package com.example.gourmetsearcher.manager

import android.util.LruCache
import com.example.gourmetsearcher.model.api.RestaurantList
import com.example.gourmetsearcher.model.data.SearchTerms
import retrofit2.Response
import javax.inject.Singleton

/**
 * キャッシュマネージャ
 * @param cache キャッシュ
 */
@Singleton
class CacheManager(
    private val cache: LruCache<SearchTerms, Response<RestaurantList>?>,
) {
    /**
     *  キャッシュから結果を取得する
     * @param searchTerms 検索条件
     * @return レストラン情報 or null
     */
    fun get(searchTerms: SearchTerms): Response<RestaurantList>? = cache[searchTerms]

    /**
     * キャッシュに結果を保存する
     * @param searchTerms 検索条件
     * @param response レストラン情報
     */
    fun put(
        searchTerms: SearchTerms,
        response: Response<RestaurantList>?,
    ) {
        cache.put(searchTerms, response)
    }
}
