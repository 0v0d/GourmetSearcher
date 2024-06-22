package com.example.gourmetsearcher.repository

import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject
import kotlin.coroutines.resume

/** 位置情報の取得のリポジトリ */
interface SearchLocationRepository {
    suspend fun getLocation(): Location?
}

/**
 * LocationRepositoryの実装クラス
 * @param locationProvider 位置情報のプロバイダ
 */
class SearchLocationRepositoryImpl
    @Inject
    constructor(
        private val locationProvider: FusedLocationProviderClient,
    ) : SearchLocationRepository {
        /**
         * 位置情報を取得
         * @return 位置情報 or null
         */
        override suspend fun getLocation(): Location? =
            withContext(Dispatchers.IO) {
                try {
                    // 20秒以内に取得できなかった場合はnullを返す
                    withTimeoutOrNull(20000L) {
                        fetchLocation()
                    }
                } catch (e: Exception) {
                    println(e.message)
                    null
                }
            }

        /**
         * 位置情報を取得
         * @return 位置情報 or null
         */
        private suspend fun fetchLocation(): Location? =
            suspendCancellableCoroutine { continuation ->
                try {
                    locationProvider
                        .getCurrentLocation(Priority.PRIORITY_LOW_POWER, null)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                continuation.resume(task.result)
                            } else {
                                continuation.resume(null)
                            }
                        }
                } catch (e: SecurityException) {
                    continuation.resume(null)
                } catch (e: Exception) {
                    continuation.resume(null)
                }
            }
    }
