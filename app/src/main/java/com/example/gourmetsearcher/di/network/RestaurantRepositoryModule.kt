package com.example.gourmetsearcher.di.network

import com.example.gourmetsearcher.manager.CacheManager
import com.example.gourmetsearcher.repository.RestaurantRepository
import com.example.gourmetsearcher.repository.RestaurantRepositoryImpl
import com.example.gourmetsearcher.service.HotPepperGourmetApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/** RestaurantRepositoryのモジュール */
@Module
@InstallIn(SingletonComponent::class)
object RestaurantRepositoryModule {
    /**
     * RestaurantRepositoryを提供
     * @param service   HotPepperGourmetApiService
     * @param cacheManager CacheManager
     * @return RestaurantRepository
     */
    @Provides
    fun provideRestaurantRepository(
        service: HotPepperGourmetApiService,
        cacheManager: CacheManager,
    ): RestaurantRepository = RestaurantRepositoryImpl(service, cacheManager)
}
