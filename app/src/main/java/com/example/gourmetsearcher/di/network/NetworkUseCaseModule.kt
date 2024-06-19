package com.example.gourmetsearcher.di.network

import com.example.gourmetsearcher.repository.HotPepperRepository
import com.example.gourmetsearcher.usecase.network.GetHotPepperDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/** ネットワークのユースケースのモジュール */
@Module
@InstallIn(ViewModelComponent::class)
object NetworkUseCaseModule {
    /**
     * HotPepperUseCaseを提供
     * @param repository HotPepperRepository
     * @return HotPepperUseCase
     */
    @Provides
    fun provideHotPepperUseCase(repository: HotPepperRepository): GetHotPepperDataUseCase = GetHotPepperDataUseCase(repository)
}
