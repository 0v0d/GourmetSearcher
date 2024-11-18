package com.example.gourmetsearcher.di.location

import android.content.Context
import com.example.gourmetsearcher.repository.SearchLocationRepository
import com.example.gourmetsearcher.repository.SearchLocationRepositoryImpl
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/** 位置情報のリポジトリのモジュール */
@Module
@InstallIn(SingletonComponent::class)
object SearchLocationModule {

    /**
     * 位置情報のリポジトリを提供する
     * @param context コンテキスト
     * @return 位置情報のリポジトリ
     */
    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(
        @ApplicationContext context: Context,
    ): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    /**
     * 位置情報のリポジトリを提供する
     * @param locationProvider 位置情報のプロバイダー
     * @return 位置情報のリポジトリ
     */
    @Provides
    @Singleton
    fun provideLocationRepository(locationProvider: FusedLocationProviderClient): SearchLocationRepository =
        SearchLocationRepositoryImpl(locationProvider)
}
