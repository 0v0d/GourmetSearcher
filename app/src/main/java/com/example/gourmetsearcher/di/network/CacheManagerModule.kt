package com.example.gourmetsearcher.di.network

import android.util.LruCache
import com.example.gourmetsearcher.manager.CacheManager
import com.example.gourmetsearcher.model.api.HotPepperResponse
import com.example.gourmetsearcher.model.data.SearchTerms
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Response

/** キャッシュマネージャのモジュール */
@Module
@InstallIn(SingletonComponent::class)
object CacheManagerModule {
    /** キャッシュサイズ */
    private const val CACHE_SIZE = 5

    /**
     * LruCacheを提供する
     * Size: 5
     * @return LruCache
     */
    @Provides
    fun provideLruCache(): LruCache<SearchTerms, Response<HotPepperResponse>?> = LruCache(CACHE_SIZE)

    /**
     * キャッシュマネージャを提供する
     * @return キャッシュマネージャ
     */
    @Provides
    fun provideCacheManager(lruCache: LruCache<SearchTerms, Response<HotPepperResponse>?>) = CacheManager(lruCache)
}
