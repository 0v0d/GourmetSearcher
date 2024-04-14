package com.example.gourmetsearcher.usecase

import com.example.gourmetsearcher.model.api.HotPepperResponse
import com.example.gourmetsearcher.model.data.SearchTerms
import retrofit2.Response

/** ホットペッパーグルメAPIを利用して、レストラン情報を取得するUseCase */
interface HotPepperUseCase {
    /** リポジトリ情報を取得
     * @param searchTerms 検索条件
     * @return Response<HotPepperResponse>レストラン情報 or null
     */
    suspend fun execute(searchTerms: SearchTerms): Response<HotPepperResponse>?
}