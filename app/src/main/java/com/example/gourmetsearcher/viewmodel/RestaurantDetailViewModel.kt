package com.example.gourmetsearcher.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.net.URLEncoder

/** レストラン詳細画面のViewModel */
class RestaurantDetailViewModel : ViewModel() {
    private val _url = MutableStateFlow<String?>(null)

    /** URLを開くためのStateFlow */
    val url = _url.asStateFlow()

    private val _address = MutableStateFlow<String?>(null)

    /** 住所を検索するためのStateFlow */
    val address = _address.asStateFlow()

    /** ボタンクリック時に住所をエンコードしてURLを開く */
    fun openMap(address: String) {
        _address.value = URLEncoder.encode(address, "UTF-8")
    }

    /** 住所をクリアする */
    fun clearAddress() {
        _address.value = null
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
