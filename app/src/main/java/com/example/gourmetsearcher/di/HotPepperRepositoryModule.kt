package com.example.gourmetsearcher.di

import com.example.gourmetsearcher.repository.HotPepperRepository
import com.example.gourmetsearcher.usecase.HotPepperUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object HotPepperRepositoryModule {
    @Provides
    fun provideHotPepperRepository(repository: HotPepperRepository): HotPepperUseCase {
        return repository
    }
}