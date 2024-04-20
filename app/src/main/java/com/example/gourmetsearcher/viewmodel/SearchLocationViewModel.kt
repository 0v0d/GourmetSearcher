package com.example.gourmetsearcher.viewmodel

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gourmetsearcher.model.data.CurrentLocation
import com.example.gourmetsearcher.state.LocationSearchState
import com.example.gourmetsearcher.usecase.FusedLocationProviderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 現在地を取得するためのViewModel
 * @param locationProviderUseCase 位置情報を取得するUseCase
 */
@HiltViewModel
class SearchLocationViewModel @Inject constructor(
    private val locationProviderUseCase: FusedLocationProviderUseCase
) : ViewModel() {
    private val _locationData = MutableStateFlow<CurrentLocation?>(null)

    /** 現在地のデータ */
    val locationData = _locationData.asStateFlow()

    private val _openLocationSettingEvent = MutableSharedFlow<Unit>()

    /**現在地取得に失敗した場合に設定画面を開くためのイベント */
    val openLocationSettingEvent = _openLocationSettingEvent.asSharedFlow()

    private val _retryEvent = MutableSharedFlow<Unit>()

    /** 現在地取得に失敗した場合にリトライするためのイベント */
    val retryEvent = _retryEvent.asSharedFlow()

    /**
     *現在地取得の状態
     * LOADING: 現在地取得中
     * ERROR: 現在地取得失敗
     */
    private val _searchState = MutableStateFlow(LocationSearchState.LOADING)
    val searchState = _searchState.asStateFlow()

    /** 現在地を取得する */
    fun getLocation() {
        viewModelScope.launch {
            try {
                performSearch()
            } catch (e: SecurityException) {
                _searchState.value = LocationSearchState.ERROR
            } catch (e: Exception) {
                _searchState.value = LocationSearchState.ERROR
            }
        }
    }

    /** 現在地を取得するための処理 */
    private suspend fun performSearch() {
        val location = locationProviderUseCase.getLocation()
        if (location != null) {
            handleLocationSuccess(location)
            return
        }
        _searchState.value = LocationSearchState.ERROR
    }

    /** 現在地取得に成功した場合の処理
     * @param location 現在地の緯度経度
     */
    private fun handleLocationSuccess(location: Location) {
        val locationData = CurrentLocation(location.latitude, location.longitude)
        _locationData.value = locationData
    }

    /** 現在地取得に失敗した場合に設定画面を開くための処理 */
    fun onOpenLocationSettingClicked() {
        viewModelScope.launch {
            _openLocationSettingEvent.emit(Unit)
        }
    }

    /** 現在地取得に失敗した場合のリトライ処理 */
    fun onRetryClicked() {
        viewModelScope.launch {
            _retryEvent.emit(Unit)
        }
    }
}