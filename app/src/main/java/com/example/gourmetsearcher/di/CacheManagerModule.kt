package com.example.gourmetsearcher.di

import com.example.gourmetsearcher.repository.CacheManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/** キャッシュマネージャのモジュール */
@Module
@InstallIn(SingletonComponent::class)
object CacheManagerModule {
    /**
     * キャッシュマネージャを提供する
     * @return キャッシュマネージャ
     */
    @Provides
    @Singleton
    fun provideCacheManager(): CacheManager {
        // キャッシュサイズ
        val cacheSize = 5
        return CacheManager(cacheSize)
    }
}