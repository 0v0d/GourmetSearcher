package com.example.gourmetsearcher.repository

import com.example.gourmetsearcher.BuildConfig
import com.example.gourmetsearcher.model.api.HotPepperResponse
import com.example.gourmetsearcher.model.data.SearchTerms
import com.example.gourmetsearcher.source.HotPepperNetworkDataSource
import com.example.gourmetsearcher.usecase.HotPepperUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ホットペッパーグルメAPIを利用して、レストラン情報を取得するRepository
 * @param service ホットペッパーグルメAPIのインターフェース
 */
@Singleton
class HotPepperRepository @Inject constructor
    (private val service: HotPepperNetworkDataSource) : HotPepperUseCase {
    private val key = BuildConfig.API_KEY
    private val format = "json"

    /** リポジトリ情報を取得
     * @param searchTerms 検索条件
     * @return Response<HotPepperResponse>レストラン情報 or null
     */
    override suspend fun execute(searchTerms: SearchTerms): Response<HotPepperResponse>? {
        return searchHotPepperRepository(searchTerms)
    }

    /**
     * ホットペッパーグルメAPIを利用して、レストラン情報を取得
     * @param searchTerms 検索条件
     * @return Response<HotPepperResponse>レストラン情報 or null
     */
    private suspend fun searchHotPepperRepository(searchTerms: SearchTerms): Response<HotPepperResponse>? =
        withContext(Dispatchers.IO)
        {
            return@withContext try {
                service.getRestaurantDatum(
                    key,
                    searchTerms.keyword,
                    searchTerms.location.lat,
                    searchTerms.location.lng,
                    searchTerms.range,
                    format
                )
            } catch (e: Exception) {
                null
            }
        }


}