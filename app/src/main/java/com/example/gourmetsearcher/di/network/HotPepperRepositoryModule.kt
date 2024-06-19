package com.example.gourmetsearcher.di.network

import com.example.gourmetsearcher.manager.CacheManager
import com.example.gourmetsearcher.repository.HotPepperRepository
import com.example.gourmetsearcher.repository.HotPepperRepositoryImpl
import com.example.gourmetsearcher.source.HotPepperNetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/** ネットワークリポジトリのモジュール */
@Module
@InstallIn(SingletonComponent::class)
object HotPepperRepositoryModule {
    /**
     * HotPepperRepositoryを提供
     * @param service HotPepperNetworkDataSource
     * @param cacheManager CacheManager
     * @return HotPepperRepository
     */
    @Provides
    fun provideHotPepperRepository(
        service: HotPepperNetworkDataSource,
        cacheManager: CacheManager,
    ): HotPepperRepository = HotPepperRepositoryImpl(service, cacheManager)
}
