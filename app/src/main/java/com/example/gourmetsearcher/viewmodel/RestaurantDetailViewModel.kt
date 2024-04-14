package com.example.gourmetsearcher.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.net.URLEncoder

/** レストラン詳細画面のViewModel */
class RestaurantDetailViewModel : ViewModel() {
    private val _url = MutableLiveData<String?>()
    /** URLを開くためのLiveData */
    val url: MutableLiveData<String?> = _url

    private val _searchAddress = MutableLiveData<String?>()
    /** 住所をエンコードしてURLを開くためのLiveData */
    val searchAddress: MutableLiveData<String?> = _searchAddress

    /** ボタンクリック時に住所をエンコードしてURLを開く */
    fun openMap(address: String) {
        _searchAddress.value = URLEncoder.encode(address, "UTF-8")
    }

    /** 住所をクリアする */
    fun clearAddress() {
        _searchAddress.value = null
    }

    /** ボタンクリック時にURLを開く */
    fun openUrl(htmlUrl: String) {
        _url.value = htmlUrl
    }

    /** URLをクリアする */
    fun clearUrl() {
        _url.value = null
    }
}