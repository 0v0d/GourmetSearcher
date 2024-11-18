package com.example.gourmetsearcher.di.keywordhistory

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.gourmetsearcher.manager.PreferencesManager
import com.example.gourmetsearcher.repository.KeyWordHistoryRepository
import com.example.gourmetsearcher.repository.KeyWordHistoryRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/** 検索履歴のモジュール */
@Module
@InstallIn(SingletonComponent::class)
object KeyWordHistoryModule {
    /** データストアを提供する */
    private val Context.dataStore: DataStore<Preferences>
    by preferencesDataStore(name = "HistoryPrefs")

    /**
     * データストアを提供する
     * @param context コンテキスト
     * @return データストア
     */
    @Provides
    @Singleton
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
    fun providePreferencesManger(dataStore: DataStore<Preferences>): PreferencesManager
    = PreferencesManager(dataStore)

    /**
     * 検索履歴のリポジトリを提供する
     * @param preferencesManager プリファレンスマネージャ
     * @return 検索履歴のリポジトリ
     */
    @Provides
    @Singleton
    fun provideKeyWordHistory(preferencesManager: PreferencesManager): KeyWordHistoryRepository =
        KeyWordHistoryRepositoryImpl(preferencesManager)
}
