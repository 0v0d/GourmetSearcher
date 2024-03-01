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

class SearchLocationRepository @Inject constructor(
    private val locationProvider: FusedLocationProviderClient,
) {
    suspend fun getLocation(): Location? {
        return try {
            // 20秒以内に位置情報を取得できなかった場合はnullを返す
            withTimeoutOrNull(20000L) {
                fetchLocation()
            }
        } catch (e: Exception) {
            logError("${e.message}")
            null
        }
    }

    private suspend fun fetchLocation(): Location? = suspendCancellableCoroutine { continuation ->
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

    private fun logError(message: String) {
        Log.e("LocationError", message)
    }
}