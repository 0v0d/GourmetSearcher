package com.example.gourmetsearcher.di

import com.example.gourmetsearcher.repository.HotPepperRepository
import com.example.gourmetsearcher.repository.HotPepperRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/** 位置情報のユースケースのモジュール */
@Module
@InstallIn(ViewModelComponent::class)
object LocationUseCaseModule {
    /**
     * HotPepperRepositoryを提供
     * @param repository HotPepperRepository
     * @return HotPepperRepository
     */
    @Provides
    fun provideHotPepperRepository(repository: HotPepperRepositoryImpl): HotPepperRepository = repository
}
