package com.example.gourmetsearcher.usecase

import android.location.Location

/** 現在の位置情報を取得するUseCase */
interface FusedLocationProviderUseCase {
    /** 現在の位置情報を取得
     * @return Location? 現在の位置情報 or null
     */
    suspend fun getLocation(): Location?
}