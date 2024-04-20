package com.example.gourmetsearcher.di

import android.content.Context
import com.example.gourmetsearcher.repository.KeyWordHistoryRepository
import com.example.gourmetsearcher.usecase.KeyWordHistoryUseCase
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
     * @param context コンテキスト
     * @return 検索履歴のリポジトリ
     */
    @Provides
    @ViewModelScoped
    fun provideKeyWordHistoryUseCase(@ApplicationContext context: Context): KeyWordHistoryUseCase {
        return KeyWordHistoryRepository(context)
    }
}