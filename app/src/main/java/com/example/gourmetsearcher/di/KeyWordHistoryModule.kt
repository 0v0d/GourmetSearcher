package com.example.gourmetsearcher.di

import android.content.Context
import com.example.gourmetsearcher.repository.KeyWordHistoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object KeyWordHistoryModule {
    @Provides
    fun provideKeyWordHistoryRepository(@ApplicationContext context: Context): KeyWordHistoryRepository {
        return KeyWordHistoryRepository(context)
    }

}