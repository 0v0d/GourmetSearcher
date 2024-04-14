package com.example.gourmetsearcher.viewmodel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gourmetsearcher.model.data.CurrentLocation
import com.example.gourmetsearcher.repository.SearchLocationRepository
import com.example.gourmetsearcher.state.LocationSearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 現在地を取得するためのViewModel
 * @param locationRepository 現在地を取得するためのリポジトリ
 */
@HiltViewModel
class SearchLocationViewModel @Inject constructor(
    private val locationRepository: SearchLocationRepository,
) : ViewModel() {
    private val _locationData = MutableLiveData<CurrentLocation>()

    /** 現在地のデータ */
    val locationData: LiveData<CurrentLocation> = _locationData

    private val _openLocationSettingEvent = MutableLiveData<Unit>()

    /**現在地取得に失敗した場合に設定画面を開くためのイベント */
    val openLocationSettingEvent: LiveData<Unit> = _openLocationSettingEvent

    private val _retryEvent = MutableLiveData<Unit>()

    /** 現在地取得に失敗した場合にリトライするためのイベント */
    val retryEvent: LiveData<Unit> = _retryEvent

    /**
     *現在地取得の状態
     * LOADING: 現在地取得中
     * ERROR: 現在地取得失敗
     */
    private val _searchState = MutableLiveData(LocationSearchState.LOADING)
    val searchState: LiveData<LocationSearchState> get() = _searchState

    /** 現在地を取得する */
    fun getLocation() {
        viewModelScope.launch {
            try {
                performSearch()
            } catch (e: SecurityException) {
                _searchState.postValue(LocationSearchState.ERROR)
            } catch (e: Exception) {
                _searchState.postValue(LocationSearchState.ERROR)
            }
        }
    }

    /** 現在地を取得するための処理 */
    private suspend fun performSearch() {
        val location = locationRepository.getLocation()
        if (location != null) {
            handleLocationSuccess(location)
            return
        }
        _searchState.postValue(LocationSearchState.ERROR)
    }

    /** 現在地取得に成功した場合の処理*/
    private fun handleLocationSuccess(location: Location) {
        val locationData = CurrentLocation(location.latitude, location.longitude)
        _locationData.value = locationData
    }

    /** 現在地取得に失敗した場合に設定画面を開くための処理 */
    fun onOpenLocationSettingClicked() {
        _openLocationSettingEvent.value = Unit
    }

    /** 現在地取得に失敗した場合のリトライ処理 */
    fun onRetryClicked() {
        _retryEvent.value = Unit
    }
}