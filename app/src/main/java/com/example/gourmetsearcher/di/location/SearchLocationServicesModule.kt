package com.example.gourmetsearcher.di.location

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/** 位置情報のモジュール */
@Module
@InstallIn(SingletonComponent::class)
object SearchLocationServicesModule {
    /**
     * 位置情報のリポジトリを提供する
     * @param context コンテキスト
     * @return 位置情報のリポジトリ
     */
    @Provides
    fun provideFusedLocationProviderClient(
        @ApplicationContext context: Context,
    ): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
}
