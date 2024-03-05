package com.example.gourmetsearcher.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.net.URLEncoder

class RestaurantDetailViewModel : ViewModel() {
    private val _url = MutableLiveData<String>()
    val url: LiveData<String> get() = _url

    private val _searchAddress = MutableLiveData<String>()
    val searchAddress: LiveData<String> get() = _searchAddress

    fun openMap(address: String) {
        _searchAddress.value = URLEncoder.encode(address, "UTF-8")
    }

    fun openUrl(htmlUrl: String) {
        _url.value = htmlUrl
    }
}