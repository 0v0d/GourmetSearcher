package com.example.gourmetsearcher.viewmodel

import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gourmetsearcher.model.LastLocation
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener

class SearchLocationViewModel : ViewModel() {
    private val _locationData = MutableLiveData<LastLocation>()
    val locationData: LiveData<LastLocation> get() = _locationData

    private val _errorState = MutableLiveData<Boolean>()
    val errorState: LiveData<Boolean> get() = _errorState

    fun getLocation(context: Context) {
        try {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient.lastLocation.addOnCompleteListener(locationCompleteListener)
        } catch (e: SecurityException) {
            handleLocationError()
        }
    }

    private val locationCompleteListener = OnCompleteListener { task ->
        try {
            if (task.isSuccessful && task.result != null) {
                handleLocationSuccess(task.result)
            } else {
                handleLocationError()
            }
        } catch (e: Exception) {
            handleLocationError()
        }
    }

    private fun handleLocationSuccess(location: Location) {
        val locationData = LastLocation(location.latitude, location.longitude)
        _locationData.value = locationData
    }

    private fun handleLocationError() {
        _errorState.value = true
    }
}