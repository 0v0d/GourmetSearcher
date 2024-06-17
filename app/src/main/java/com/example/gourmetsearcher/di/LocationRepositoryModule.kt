package com.example.gourmetsearcher.di

import com.example.gourmetsearcher.repository.LocationRepository
import com.example.gourmetsearcher.repository.LocationRepositoryImpl
import com.example.gourmetsearcher.usecase.GetCurrentLocationUseCase
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/** 位置情報のモジュール */
@Module
@InstallIn(SingletonComponent::class)
object LocationRepositoryModule {
    /**
     * 位置情報のリポジトリを提供する
     * @param locationProvider 位置情報のプロバイダー
     * @return 位置情報のリポジトリ
     */
    @Provides
    fun provideLocationRepository(locationProvider: FusedLocationProviderClient): LocationRepository =
        LocationRepositoryImpl(locationProvider)

    /**
     * 位置情報のユースケースを提供する
     * @param locationRepository 位置情報のリポジトリ
     * @return 位置情報のユースケース
     */
    @Provides
    fun provideLocationUseCase(locationRepository: LocationRepository): GetCurrentLocationUseCase =
        GetCurrentLocationUseCase(locationRepository)
}
