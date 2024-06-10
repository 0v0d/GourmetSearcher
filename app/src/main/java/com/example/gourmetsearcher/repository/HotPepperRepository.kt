package com.example.gourmetsearcher.repository

import com.example.gourmetsearcher.BuildConfig
import com.example.gourmetsearcher.manager.CacheManager
import com.example.gourmetsearcher.model.api.HotPepperResponse
import com.example.gourmetsearcher.model.data.SearchTerms
import com.example.gourmetsearcher.source.HotPepperNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/** ホットペッパーグルメAPIを利用して、レストラン情報を取得する */
interface HotPepperRepository {
    /** リポジトリ情報を取得
     * @param searchTerms 検索条件
     * @return Response<HotPepperResponse>レストラン情報 or null
     */
    suspend fun execute(searchTerms: SearchTerms): Response<HotPepperResponse>?
}

/**
 *  HotPepperRepositoryの実装クラス
 * @param service ホットペッパーグルメAPIのインターフェース
 */
@Singleton
class HotPepperRepositoryImpl
    @Inject
    constructor(
        private val service: HotPepperNetworkDataSource,
        private val cacheManager: CacheManager,
    ) : HotPepperRepository {
        /** APIキー */
        private val key = BuildConfig.API_KEY

        /** レスポンスフォーマット */
        private val format = "json"

        /** リポジトリ情報を取得
         * @param searchTerms 検索条件
         * @return レストラン情報 or null
         */
        override suspend fun execute(searchTerms: SearchTerms): Response<HotPepperResponse>? {
            return searchHotPepperRepository(searchTerms)
        }

        /**
         * ホットペッパーグルメAPIを利用して、レストラン情報を取得
         * @param searchTerms 検索条件
         * @return レストラン情報 or null
         */
        private suspend fun searchHotPepperRepository(searchTerms: SearchTerms): Response<HotPepperResponse>? =
            withContext(Dispatchers.IO) {
                // キャッシュから結果を取得
                cacheManager.get(searchTerms)?.let { return@withContext it }

                return@withContext try {
                    val response =
                        service.getRestaurantDatum(
                            key,
                            searchTerms.keyword,
                            searchTerms.location.lat,
                            searchTerms.location.lng,
                            searchTerms.range,
                            format,
                        )
                    cacheManager.put(searchTerms, response)
                    response
                } catch (e: Exception) {
                    null
                }
            }
    }
