package com.example.gourmetsearcher.di.network

import com.example.gourmetsearcher.service.HotPepperGourmetApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

/** ネットワークサービスのモジュール */
@Module
@InstallIn(SingletonComponent::class)
object NetWorkServiceModule {
    /**
     *  HotPepperGourmetApiServiceを提供
     * @param retrofit Retrofit
     * @return HotPepperGourmetApiService
     */
    @Provides
    fun provideNetWorkServiceModule(
        retrofit: Retrofit
    ): HotPepperGourmetApiService = retrofit.create(HotPepperGourmetApiService::class.java)
}
