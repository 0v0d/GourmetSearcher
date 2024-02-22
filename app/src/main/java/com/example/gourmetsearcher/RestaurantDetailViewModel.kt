package com.example.gourmetsearcher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.URLEncoder


class RestaurantDetailViewModel : ViewModel(){
    private val _url = MutableLiveData<String>()
    val url: LiveData<String> get() = _url

    fun openMap(address: String){
        val searchAddress = URLEncoder.encode(address, "UTF-8")
        val url = "geo:0:0?q=${searchAddress}"
        openUrl(url)
    }

    fun openUrl(htmlUrl: String) {
        _url.value = htmlUrl
    }
}