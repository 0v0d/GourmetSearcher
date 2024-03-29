package com.example.gourmetsearcher.di

import android.content.Context
import com.example.gourmetsearcher.R
import com.example.gourmetsearcher.source.HotPepperNetworkDataSource
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * ネットワーク関連のモジュール
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    /**
     * Moshiを提供
     * @return Moshi
     */
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    /**
     * OkHttpClientを提供
     * @return OkHttpClient
     */
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    /**
     * Retrofitを提供
     * @param context Context
     * @param moshi Moshi
     * @param okHttpClient OkHttpClient
     * @return Retrofit
     */
    @Provides
    fun provideRetrofit(@ApplicationContext context: Context, moshi: Moshi, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(context.getString(R.string.hot_pepper_url))
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .build()

    /**
     * HotPepperNetworkDataSourceを提供
     * @param retrofit Retrofit
     * @return HotPepperNetworkDataSource
     */
    @Provides
    fun provideHotPepperService(retrofit: Retrofit): HotPepperNetworkDataSource =
        retrofit.create(HotPepperNetworkDataSource::class.java)
}