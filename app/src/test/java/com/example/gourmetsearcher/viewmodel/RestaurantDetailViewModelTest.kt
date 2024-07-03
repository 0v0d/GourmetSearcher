package com.example.gourmetsearcher.viewmodel

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnitRunner
import java.net.URLEncoder

/** RestaurantDetailViewModelのユニットテストクラス */
@RunWith(MockitoJUnitRunner::class)
class RestaurantDetailViewModelTest {
    @InjectMocks
    private lateinit var viewModel: RestaurantDetailViewModel

    /** 地図を開く機能のテスト */
    @Test
    fun testOpenMap() {
        val address = "東京都渋谷区道玄坂1-2-3"
        val encodedAddress = URLEncoder.encode(address, "UTF-8")

        viewModel.openMap(address)

        assertEquals(encodedAddress, viewModel.address.value)
    }

    /** 住所をクリアする機能のテスト */
    @Test
    fun testClearAddress() {
        viewModel.clearAddress()

        assertEquals(null, viewModel.address.value)
    }

    /** URLを開く機能のテスト */
    @Test
    fun testOpenUrl() {
        val url = "https://example.com"

        viewModel.openUrl(url)

        assertEquals(url, viewModel.url.value)
    }

    /** URLをクリアする機能のテスト */
    @Test
    fun testClearUrl() {
        viewModel.clearUrl()

        assertEquals(null, viewModel.url.value)
    }
}
