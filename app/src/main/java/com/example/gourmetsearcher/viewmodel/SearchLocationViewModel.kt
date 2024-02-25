package com.example.gourmetsearcher.viewmodel

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.gourmetsearcher.model.LastLocation
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener


enum class LocationSearchState {
    LOADING,
    ERROR,
    SUCCESS,
}

class SearchLocationViewModel : ViewModel() {
    private val _locationData = MutableLiveData<LastLocation>()
    val locationData: LiveData<LastLocation> get() = _locationData

    private val _searchState = MutableLiveData<LocationSearchState>()
    val searchState: LiveData<LocationSearchState> get() = _searchState

    fun getLocation(context: Context) {
        try {
            _searchState.value = LocationSearchState.LOADING
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient.lastLocation.addOnCompleteListener(locationCompleteListener)
        } catch (e: SecurityException) {
            _searchState.value = LocationSearchState.ERROR
        }
    }

    private val locationCompleteListener = OnCompleteListener { task ->
        try {
            if (task.isSuccessful && task.result != null) {
                handleLocationSuccess(task.result)
                _searchState.value = LocationSearchState.SUCCESS
            } else {
                _searchState.value = LocationSearchState.ERROR
            }
        } catch (e: Exception) {
            _searchState.value = LocationSearchState.ERROR
        }
    }

    private fun handleLocationSuccess(location: Location) {
        Log.d("SearchLocationViewModel", "Location: ${location.latitude}, ${location.longitude}")
        val locationData = LastLocation(location.latitude, location.longitude)
        _locationData.value = locationData
    }
}