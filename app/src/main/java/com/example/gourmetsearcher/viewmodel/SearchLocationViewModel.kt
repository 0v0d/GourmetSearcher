package com.example.gourmetsearcher.viewmodel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gourmetsearcher.model.CurrentLocation
import com.example.gourmetsearcher.repository.SearchLocationRepository
import com.example.gourmetsearcher.state.LocationSearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchLocationViewModel @Inject constructor(
    private val locationRepository: SearchLocationRepository,
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
        viewModelScope.launch {
            try {
                performSearch()
            } catch (e: SecurityException) {
                _searchState.postValue(LocationSearchState.ERROR)
            }
        }
    }

    private suspend fun performSearch() {
        val location =  withContext(Dispatchers.IO) {
            locationRepository.getLocation()
        }
        if (location != null) {
            handleLocationSuccess(location)
        } else {
            _searchState.postValue(LocationSearchState.ERROR)
        }
    }

    private fun handleLocationSuccess(location: Location) {
        //val locationData = CurrentLocation(34.7010289,135.4955003)//デバッグ用の仮の座標
        val locationData = CurrentLocation(location.latitude, location.longitude)
        _locationData.value = locationData
    }

    fun onOpenLocationSettingClicked() {
        _openLocationSettingEvent.value = Unit
    }

    fun onRetryClicked() {
        _retryEvent.value = Unit
    }
}