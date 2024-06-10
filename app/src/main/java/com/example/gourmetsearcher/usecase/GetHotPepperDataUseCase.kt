package com.example.gourmetsearcher.usecase

import com.example.gourmetsearcher.model.data.SearchTerms
import com.example.gourmetsearcher.repository.HotPepperRepository
import javax.inject.Inject

/**
 * ホットペッパーAPIからデータを取得するUseCase
 * @param hotPepperRepository HotPepperRepository
 */
class GetHotPepperDataUseCase
    @Inject
    constructor(private val hotPepperRepository: HotPepperRepository) {
        /**
         * ホットペッパーAPIからデータを取得する
         * @param searchTerms 検索条件
         * @return Response<HotPepperResponse>レストラン情報 or null
         */
        suspend operator fun invoke(searchTerms: SearchTerms) = hotPepperRepository.execute(searchTerms)
    }
