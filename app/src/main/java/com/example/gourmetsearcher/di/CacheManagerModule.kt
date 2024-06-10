package com.example.gourmetsearcher.di

import com.example.gourmetsearcher.manager.CacheManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/** キャッシュマネージャのモジュール */
@Module
@InstallIn(SingletonComponent::class)
object CacheManagerModule {
    /**
     * キャッシュマネージャを提供する
     * @return キャッシュマネージャ
     */
    @Provides
    fun provideCacheManager(): CacheManager {
        // キャッシュサイズ
        val cacheSize = 5
        return CacheManager(cacheSize)
    }
}
