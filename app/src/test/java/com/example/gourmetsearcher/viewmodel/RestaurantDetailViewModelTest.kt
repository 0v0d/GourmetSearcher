package com.example.gourmetsearcher.viewmodel

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.URLEncoder

class RestaurantDetailViewModelTest {

    private lateinit var viewModel: RestaurantDetailViewModel

    @Before
    fun setUp() {
        viewModel = RestaurantDetailViewModel()
    }

    @Test
    fun `openMap should encode the address and update searchAddress StateFlow`() {
        val address = "東京都渋谷区道玄坂1-2-3"
        val encodedAddress = URLEncoder.encode(address, "UTF-8")

        viewModel.openMap(address)

        assertEquals(encodedAddress, viewModel.searchAddress.value)
    }

    @Test
    fun `clearAddress should update searchAddress StateFlow to null`() {
        viewModel.clearAddress()

        assertEquals(null, viewModel.searchAddress.value)
    }

    @Test
    fun `openUrl should update url StateFlow with the provided URL`() {
        val url = "https://example.com"

        viewModel.openUrl(url)

        assertEquals(url, viewModel.url.value)
    }

    @Test
    fun `clearUrl should update url StateFlow to null`() {
        viewModel.clearUrl()

        assertEquals(null, viewModel.url.value)
    }
}