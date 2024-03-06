package com.example.gourmetsearcher.di

import android.content.Context
import com.example.gourmetsearcher.repository.KeyWordHistoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/** 検索履歴のモジュール */
@Module
@InstallIn(SingletonComponent::class)
object KeyWordHistoryModule {
    /**
     * 検索履歴のリポジトリを提供する
     * @param context コンテキスト
     * @return 検索履歴のリポジトリ
     */
    @Provides
    fun provideKeyWordHistoryRepository(@ApplicationContext context: Context): KeyWordHistoryRepository {
        return KeyWordHistoryRepository(context)
    }
}