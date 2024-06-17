package com.example.gourmetsearcher.di

import com.example.gourmetsearcher.manager.PreferencesManager
import com.example.gourmetsearcher.repository.KeyWordHistoryRepository
import com.example.gourmetsearcher.repository.KeyWordHistoryRepositoryImpl
import com.example.gourmetsearcher.usecase.ClearKeyWordHistoryUseCase
import com.example.gourmetsearcher.usecase.GetKeyWordHistoryUseCase
import com.example.gourmetsearcher.usecase.SaveKeyWordHistoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

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
    fun provideKeyWordHistory(preferencesManager: PreferencesManager): KeyWordHistoryRepository =
        KeyWordHistoryRepositoryImpl(preferencesManager)

    /**
     * 検索履歴のユースケースを提供する
     * @param repository 検索履歴のリポジトリ
     * @return 検索履歴を得るためのユースケース
     */
    @Provides
    fun provideGetKeyWordHistoryUseCase(repository: KeyWordHistoryRepository): GetKeyWordHistoryUseCase =
        GetKeyWordHistoryUseCase(repository)

    /**
     * 検索履歴のユースケースを提供する
     * @param repository 検索履歴のリポジトリ
     * @return 検索履歴保存のためのユースケース
     */
    @Provides
    fun provideSaveKeyWordHistoryUseCase(repository: KeyWordHistoryRepository) = SaveKeyWordHistoryUseCase(repository)

    /**
     * 検索履歴のユースケースを提供する
     * @param repository 検索履歴のリポジトリ
     * @return 検索履歴クリアするのためのユースケース
     */
    @Provides
    fun provideClearKeyWordHistoryUseCase(repository: KeyWordHistoryRepository) = ClearKeyWordHistoryUseCase(repository)
}
