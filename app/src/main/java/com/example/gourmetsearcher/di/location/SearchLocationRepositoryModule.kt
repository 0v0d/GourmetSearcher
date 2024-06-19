package com.example.gourmetsearcher.di.location

import com.example.gourmetsearcher.repository.SearchLocationRepository
import com.example.gourmetsearcher.repository.SearchLocationRepositoryImpl
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/** 位置情報のリポジトリのモジュール */
@Module
@InstallIn(SingletonComponent::class)
object SearchLocationRepositoryModule {
    /**
     * 位置情報のリポジトリを提供する
     * @param locationProvider 位置情報のプロバイダー
     * @return 位置情報のリポジトリ
     */
    @Provides
    fun provideLocationRepository(locationProvider: FusedLocationProviderClient): SearchLocationRepository =
        SearchLocationRepositoryImpl(locationProvider)
}
