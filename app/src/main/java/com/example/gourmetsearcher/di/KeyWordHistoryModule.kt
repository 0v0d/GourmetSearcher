package com.example.gourmetsearcher.di

import android.content.Context
import com.example.gourmetsearcher.repository.KeyWordHistoryRepository
import com.example.gourmetsearcher.repository.KeyWordHistoryRepositoryImpl
import com.example.gourmetsearcher.repository.PreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

/** 検索履歴のモジュール */
@Module
@InstallIn(ViewModelComponent::class)
object KeyWordHistoryModule {
    /**
     * 検索履歴のリポジトリを提供する
     * @param preferencesManager プリファレンスマネージャ
     * @return 検索履歴のリポジトリ
     */
    @Provides
    @ViewModelScoped
    fun provideKeyWordHistory(preferencesManager: PreferencesManager): KeyWordHistoryRepository {
        return KeyWordHistoryRepositoryImpl(preferencesManager)
    }

    /**
     * プリファレンスマネージャを提供する
     * @param context コンテキスト
     * @return プリファレンスマネージャ
     */
    @Provides
    fun providePreferencesManger(
        @Suppress("ktlint:standard:function-signature")
        @ApplicationContext context: Context,
    ): PreferencesManager {
        return PreferencesManager(context)
    }
}
