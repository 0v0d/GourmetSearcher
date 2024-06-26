package com.example.gourmetsearcher.di.keywordhistory

import android.content.Context
import com.example.gourmetsearcher.manager.PreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/** プリファレンスのモジュール */
@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {
    /**
     * プリファレンスマネージャを提供する
     * @param context コンテキスト
     * @return プリファレンスマネージャ
     */
    @Provides
    @Singleton
    fun providePreferencesManger(
        @ApplicationContext context: Context,
    ): PreferencesManager = PreferencesManager(context)
}
