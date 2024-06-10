package com.example.gourmetsearcher.di

import android.content.Context
import com.example.gourmetsearcher.manager.PreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PreferencesModule {
    /**
     * プリファレンスマネージャを提供する
     * @param context コンテキスト
     * @return プリファレンスマネージャ
     */
    @Provides
    fun providePreferencesManger(
        @ApplicationContext context: Context,
    ): PreferencesManager = PreferencesManager(context)
}
