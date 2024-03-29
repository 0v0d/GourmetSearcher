package com.example.gourmetsearcher.repository

import android.location.Location
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

/**
 * 現在の位置情報を取得するRepository
 * @param locationProvider 位置情報を取得するためのクライアント
 */
class SearchLocationRepository @Inject constructor(
    private val locationProvider: FusedLocationProviderClient,
) {
    /**
     * 現在の位置情報を取得
     * @return 現在の位置情報 or null
     */
    suspend fun getLocation(): Location? {
        return try {
            /** 20秒以内に位置情報を取得できなかった場合はnullを返す*/
            withTimeoutOrNull(20000L) {
                fetchLocation()
            }
        } catch (e: Exception) {
            logError("${e.message}")
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
                locationProvider.getCurrentLocation(
                    Priority.PRIORITY_LOW_POWER,
                    null
                ).addOnCompleteListener { task ->
                    handleCompletion(task, continuation)
                }
            } catch (e: SecurityException) {
                logError(" ${e.message}")
                continuation.resume(null)
            } catch (e: Exception) {
                logError("${e.message} ")
                continuation.resume(null)
            }
        }

    /**
     * Taskの完了結果を処理
     * @param task Task<Location>
     * @param continuation Continuation<Location?>
     */
    private fun handleCompletion(
        task: Task<Location>,
        continuation: Continuation<Location?>
    ) {
        if (task.isSuccessful) {
            val location = task.result
            continuation.resume(location)
        } else {
            logError("${task.exception?.message}")
            continuation.resume(null)
        }
    }

    /**
     * エラーログを出力
     * @param message String
     */
    private fun logError(message: String) {
        Log.e("LocationError", message)
    }
}