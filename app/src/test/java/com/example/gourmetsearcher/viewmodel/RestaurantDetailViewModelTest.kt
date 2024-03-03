package com.example.gourmetsearcher.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import java.net.URLEncoder

class RestaurantDetailViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var urlObserver: Observer<String>

    private lateinit var viewModel: RestaurantDetailViewModel

    @Before
    fun setup() {
        viewModel = RestaurantDetailViewModel()
        viewModel.url.observeForever(urlObserver)
    }

    @After
    fun cleanup() {
        viewModel.url.removeObserver(urlObserver)
    }

    @Test
    fun `openMap encodes address and updates url LiveData`() {
        val address = "123 Main St"
        viewModel.openMap(address)
        val expectedUrl = "geo:0:0?q=" + URLEncoder.encode(address, "UTF-8")
        assertEquals(expectedUrl, viewModel.url.value)
    }

    @Test
    fun `openUrl updates url LiveData`() {
        val htmlUrl = "https://example.com"

        viewModel.openUrl(htmlUrl)

        verify(urlObserver).onChanged(htmlUrl)
        verifyNoMoreInteractions(urlObserver)
        assertEquals(htmlUrl, viewModel.url.value)
    }
}