package com.example.gourmetsearcher.di

import android.content.Context
import com.example.gourmetsearcher.R
import com.example.gourmetsearcher.repository.HotPepperRepository
import com.example.gourmetsearcher.repository.HotPepperRepositoryImpl
import com.example.gourmetsearcher.source.HotPepperNetworkDataSource
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/** ネットワーク関連のモジュール */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    /**
     * Moshiを提供
     * @return Moshi
     */
    @Provides
    fun provideMoshi(): Moshi =
        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    /**
     * Retrofitを提供
     * @param context Context
     * @param moshi Moshi
     * @return Retrofit
     */
    @Provides
    fun provideRetrofit(
        @ApplicationContext context: Context,
        moshi: Moshi,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(context.getString(R.string.hot_pepper_url))
        .addConverterFactory(MoshiConverterFactory.create(moshi)).build()

    /**
     * HotPepperNetworkDataSourceを提供
     * @param retrofit Retrofit
     * @return HotPepperNetworkDataSource
     */
    @Provides
    fun provideHotPepperService(retrofit: Retrofit): HotPepperNetworkDataSource =
        retrofit.create(HotPepperNetworkDataSource::class.java)

    /**
     * HotPepperRepositoryを提供
     * @param repository HotPepperRepository
     */
    @Provides
    fun provideHotPepperRepository(repository: HotPepperRepositoryImpl): HotPepperRepository {
        return repository
    }
}