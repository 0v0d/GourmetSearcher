package com.example.gourmetsearcher.repository

import com.example.gourmetsearcher.BuildConfig
import com.example.gourmetsearcher.manager.CacheManager
import com.example.gourmetsearcher.model.api.HotPepperResponse
import com.example.gourmetsearcher.model.data.RestaurantQueryParams
import com.example.gourmetsearcher.model.data.SearchTerms
import com.example.gourmetsearcher.source.HotPepperNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.text.ParseException
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
                val params = RestaurantQueryParams(key, searchTerms, format)
                val response = service.getRestaurantDataWithObject(params)
                cacheManager.put(searchTerms, response)
                response
            } catch (e: IOException) {
                // ネットワーク関連の例外処理
                null
            } catch (e: HttpException) {
                // HTTP例外の処理
                null
            } catch (e: ParseException) {
                // パース例外の処理
                null
            } catch (e: Exception) {
                // その他の予期せぬ例外の処理
                null
            }
        }
}
