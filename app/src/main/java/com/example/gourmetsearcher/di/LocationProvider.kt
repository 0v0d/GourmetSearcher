package com.example.gourmetsearcher.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getFusedLocationProviderClient(): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }
}