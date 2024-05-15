package com.example.gourmetsearcher.di

import android.content.Context
import com.example.gourmetsearcher.repository.KeyWordHistoryRepository
import com.example.gourmetsearcher.repository.KeyWordHistoryRepositoryImpl
import com.example.gourmetsearcher.repository.PreferencesManger
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
     * @param preferencesManger プリファレンスマネージャ
     * @return 検索履歴のリポジトリ
     */
    @Provides
    @ViewModelScoped
    fun provideKeyWordHistory(preferencesManger: PreferencesManger): KeyWordHistoryRepository {
        return KeyWordHistoryRepositoryImpl(preferencesManger)
    }

    /**
     * プリファレンスマネージャを提供する
     * @param context コンテキスト
     * @return プリファレンスマネージャ
     */
    @Provides
    fun providePreferencesManger(@ApplicationContext context: Context): PreferencesManger {
        return PreferencesManger(context)
    }
}