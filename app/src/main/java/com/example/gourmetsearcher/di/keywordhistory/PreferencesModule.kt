package com.example.gourmetsearcher.di.keywordhistory

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
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
    /** データストアを提供する */
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "HistoryPrefs")

    /**
     * データストアを提供する
     * @param context コンテキスト
     * @return データストア
     */
    @Provides
    fun provideDataStore(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> = context.dataStore

    /**
     * プリファレンスマネージャを提供する
     * @param dataStore データストア
     * @return プリファレンスマネージャ
     */
    @Provides
    @Singleton
    fun providePreferencesManger(dataStore: DataStore<Preferences>): PreferencesManager = PreferencesManager(dataStore)
}
