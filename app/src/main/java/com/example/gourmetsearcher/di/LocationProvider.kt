package com.example.gourmetsearcher.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/** 位置情報のプロバイダー */
@Singleton
class LocationProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {
    /**
     * 位置情報のプロバイダーを提供する
     * @return 位置情報のプロバイダー
     */
    fun getFusedLocationProviderClient(): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }
}