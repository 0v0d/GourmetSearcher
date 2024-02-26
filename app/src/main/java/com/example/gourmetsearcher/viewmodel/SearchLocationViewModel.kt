package com.example.gourmetsearcher.viewmodel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gourmetsearcher.di.LocationProvider
import com.example.gourmetsearcher.model.LastLocation
import com.google.android.gms.tasks.OnCompleteListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


enum class LocationSearchState {
    LOADING,
    ERROR,
}

@HiltViewModel
class SearchLocationViewModel @Inject constructor(
    private val locationProvider: LocationProvider,
) : ViewModel() {
    private val _locationData = MutableLiveData<LastLocation>()
    val locationData: LiveData<LastLocation> get() = _locationData

    private val _searchState = MutableLiveData<LocationSearchState>()
    val searchState: LiveData<LocationSearchState> get() = _searchState

    private val _openLocationSettingEvent = MutableLiveData<Unit>()
    val openLocationSettingEvent: LiveData<Unit> get() = _openLocationSettingEvent
    fun getLocation() {
        try {
            _searchState.value = LocationSearchState.LOADING
            val fusedLocationClient = locationProvider.getFusedLocationProviderClient()
            fusedLocationClient.lastLocation.addOnCompleteListener(locationCompleteListener)
        } catch (e: SecurityException) {
            _searchState.value = LocationSearchState.ERROR
        }
    }

    private val locationCompleteListener = OnCompleteListener { task ->
        try {
            if (task.isSuccessful && task.result != null) {
                handleLocationSuccess(task.result)
            } else {
                _searchState.value = LocationSearchState.ERROR
            }
        } catch (e: Exception) {
            _searchState.value = LocationSearchState.ERROR
        }
    }

    private fun handleLocationSuccess(location: Location) {
        //val locationData = LastLocation(34.7010289,135.4955003)//デバッグ用の仮の座標
        val locationData = LastLocation(location.latitude, location.longitude)
        _locationData.value = locationData
    }

    fun onOpenLocationSettingClicked() {
        _openLocationSettingEvent.value = Unit
    }
}