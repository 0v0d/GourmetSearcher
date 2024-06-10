package com.example.gourmetsearcher.usecase

import com.example.gourmetsearcher.repository.LocationRepository
import javax.inject.Inject

/**
 * 現在地を取得するUseCase
 * @param locationRepository LocationRepository
 */
class GetCurrentLocationUseCase
    @Inject
    constructor(
        private val locationRepository: LocationRepository,
    ) {
        /**
         * 現在地を取得する
         * @return 位置情報 or null
         */
        suspend operator fun invoke() = locationRepository.getLocation()
    }
