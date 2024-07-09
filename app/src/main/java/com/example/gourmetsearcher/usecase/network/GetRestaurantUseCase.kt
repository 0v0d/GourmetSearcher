package com.example.gourmetsearcher.usecase.network

import com.example.gourmetsearcher.model.data.SearchTerms
import com.example.gourmetsearcher.repository.RestaurantRepository
import javax.inject.Inject

/**
 * Repositoryからレストラン情報を取得するUseCase
 * @param restaurantRepository レストラン情報のリポジトリ
 */
class GetRestaurantUseCase
@Inject
constructor(
    private val restaurantRepository: RestaurantRepository,
) {
    /**
     * Repositoryからレストラン情報を取得する
     * @param searchTerms 検索条件
     * @return Response<RestaurantList>レストラン情報 or null
     */
    suspend operator fun invoke(searchTerms: SearchTerms) =
        restaurantRepository.searchRestaurants(searchTerms)
}
