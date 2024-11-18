package com.example.gourmetsearcher.di.keywordhistory

import com.example.gourmetsearcher.repository.KeyWordHistoryRepository
import com.example.gourmetsearcher.usecase.keywordhistory.ClearKeyWordHistoryUseCase
import com.example.gourmetsearcher.usecase.keywordhistory.GetKeyWordHistoryUseCase
import com.example.gourmetsearcher.usecase.keywordhistory.SaveKeyWordHistoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/** 検索履歴のユースケースのモジュール */
@Module
@InstallIn(ViewModelComponent::class)
object KeyWordHistoryUseCaseModule {
    /**
     * 検索履歴のユースケースを提供する
     * @param repository 検索履歴のリポジトリ
     * @return 検索履歴を得るためのユースケース
     */
    @Provides
    fun provideGetKeyWordHistoryUseCase(repository: KeyWordHistoryRepository) =
        GetKeyWordHistoryUseCase(repository)

    /**
     * 検索履歴のユースケースを提供する
     * @param repository 検索履歴のリポジトリ
     * @return 検索履歴保存のためのユースケース
     */
    @Provides
    fun provideSaveKeyWordHistoryUseCase(repository: KeyWordHistoryRepository) =
        SaveKeyWordHistoryUseCase(repository)

    /**
     * 検索履歴のユースケースを提供する
     * @param repository 検索履歴のリポジトリ
     * @return 検索履歴クリアするのためのユースケース
     */
    @Provides
    fun provideClearKeyWordHistoryUseCase(repository: KeyWordHistoryRepository) =
        ClearKeyWordHistoryUseCase(repository)
}
