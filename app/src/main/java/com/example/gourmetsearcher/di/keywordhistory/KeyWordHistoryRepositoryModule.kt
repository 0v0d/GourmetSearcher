package com.example.gourmetsearcher.di.keywordhistory

import com.example.gourmetsearcher.manager.PreferencesManager
import com.example.gourmetsearcher.repository.KeyWordHistoryRepository
import com.example.gourmetsearcher.repository.KeyWordHistoryRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/** 検索履歴のモジュール */
@Module
@InstallIn(SingletonComponent::class)
object KeyWordHistoryRepositoryModule {
    /**
     * 検索履歴のリポジトリを提供する
     * @param preferencesManager プリファレンスマネージャ
     * @return 検索履歴のリポジトリ
     */
    @Provides
    fun provideKeyWordHistory(preferencesManager: PreferencesManager): KeyWordHistoryRepository =
        KeyWordHistoryRepositoryImpl(preferencesManager)
}
