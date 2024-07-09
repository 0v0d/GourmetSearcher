package com.example.gourmetsearcher.di.network

import com.example.gourmetsearcher.repository.RestaurantRepository
import com.example.gourmetsearcher.usecase.network.GetRestaurantUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/** GetRestaurantUseCaseのモジュール */
@Module
@InstallIn(ViewModelComponent::class)
object GetRestaurantUseCaseModule {
    /**
     * GetRestaurantUseCaseを提供
     * @param repository RestaurantRepository
     * @return GetRestaurantUseCase
     */
    @Provides
    fun provideGetRestaurantUseCase(
        repository: RestaurantRepository
    ): GetRestaurantUseCase = GetRestaurantUseCase(repository)
}
