package com.example.gourmetsearcher.di.network

import android.content.Context
import com.example.gourmetsearcher.R
import com.example.gourmetsearcher.manager.CacheManager
import com.example.gourmetsearcher.repository.RestaurantRepository
import com.example.gourmetsearcher.repository.RestaurantRepositoryImpl
import com.example.gourmetsearcher.service.HotPepperGourmetApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/** NetWorkのモジュール */
@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {
    /**
     * Moshiを提供
     * @return Moshi
     */
    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi
            .Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    /**
     * Retrofitを提供
     * @param context Context
     * @param moshi Moshi
     * @return Retrofit
     */
    @Provides
    @Singleton
    fun provideRetrofit(
        @ApplicationContext context: Context,
        moshi: Moshi,
    ): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(context.getString(R.string.restaurant_list_hot_pepper_url))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    /**
     *  HotPepperGourmetApiServiceを提供
     * @param retrofit Retrofit
     * @return HotPepperGourmetApiService
     */
    @Provides
    @Singleton
    fun provideNetWorkServiceModule(
        retrofit: Retrofit
    ): HotPepperGourmetApiService = retrofit.create(HotPepperGourmetApiService::class.java)

    /**
     * RestaurantRepositoryを提供
     * @param service   HotPepperGourmetApiService
     * @param cacheManager CacheManager
     * @return RestaurantRepository
     */
    @Provides
    @Singleton
    fun provideRestaurantRepository(
        service: HotPepperGourmetApiService,
        cacheManager: CacheManager,
    ): RestaurantRepository = RestaurantRepositoryImpl(service, cacheManager)
}
