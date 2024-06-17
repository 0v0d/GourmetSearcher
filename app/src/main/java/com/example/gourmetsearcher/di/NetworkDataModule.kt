package com.example.gourmetsearcher.di

import com.example.gourmetsearcher.source.HotPepperNetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

/** ネットワークデータのモジュール */
@Module
@InstallIn(SingletonComponent::class)
object NetworkDataModule {
    /**
     * HotPepperNetworkDataSourceを提供
     * @param retrofit Retrofit
     * @return HotPepperNetworkDataSource
     */
    @Provides
    fun provideHotPepperService(retrofit: Retrofit): HotPepperNetworkDataSource = retrofit.create(HotPepperNetworkDataSource::class.java)
}
