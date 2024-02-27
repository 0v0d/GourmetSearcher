package com.example.gourmetsearcher.viewmodel

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gourmetsearcher.di.LocationProvider
import com.example.gourmetsearcher.model.CurrentLocation
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnCompleteListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class LocationSearchState {
    LOADING,
    ERROR,
}

@HiltViewModel
class SearchLocationViewModel @Inject constructor(
    private val locationProvider: LocationProvider,
) : ViewModel() {
    private val _locationData = MutableLiveData<CurrentLocation>()
    val locationData: LiveData<CurrentLocation> get() = _locationData

    private val _searchState = MutableLiveData<LocationSearchState>()
    val searchState: LiveData<LocationSearchState> get() = _searchState

    private val _openLocationSettingEvent = MutableLiveData<Unit>()
    val openLocationSettingEvent: LiveData<Unit> get() = _openLocationSettingEvent

    private val _retryEvent = MutableLiveData<Unit>()
    val retryEvent: LiveData<Unit> get() = _retryEvent
    fun getLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                searchLocation()
            } catch (e: SecurityException) {
                _searchState.postValue(LocationSearchState.ERROR)
            }
        }
    }

    private fun searchLocation() {
        _searchState.postValue(LocationSearchState.LOADING)
        val fusedLocationClient = locationProvider.getFusedLocationProviderClient()
        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_LOW_POWER,
            CancellationTokenSource().token
        )
            .addOnCompleteListener(locationCompleteListener)
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
        val locationData = CurrentLocation(location.latitude, location.longitude)
        Log.d("SearchLocationViewModel", "locationData: $locationData")
        _locationData.value = locationData
    }

    fun onOpenLocationSettingClicked() {
        _openLocationSettingEvent.value = Unit
    }

    fun onRetryClicked() {
        _retryEvent.value = Unit
    }
}