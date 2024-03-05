package com.example.gourmetsearcher.viewmodel

import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.gourmetsearcher.model.CurrentLocation
import com.example.gourmetsearcher.repository.SearchLocationRepository
import com.example.gourmetsearcher.state.LocationSearchState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class SearchLocationViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var locationRepository: SearchLocationRepository

    @Mock
    private lateinit var locationDataObserver: Observer<CurrentLocation>

    @Mock
    private lateinit var searchStateObserver: Observer<LocationSearchState>

    private lateinit var viewModel: SearchLocationViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = SearchLocationViewModel(locationRepository)
        viewModel.searchState.observeForever(searchStateObserver)

        viewModel.locationData.observeForever(locationDataObserver)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }

    @Test
    fun getLocation_success() = runTest {
        val location = CurrentLocation(34.7010289, 135.4955003)
        val mockLocation = mock(Location::class.java)
        `when`(mockLocation.latitude).thenReturn(location.lat)
        `when`(mockLocation.longitude).thenReturn(location.lng)
        `when`(locationRepository.getLocation()).thenReturn(mockLocation)

        val latch = CountDownLatch(1)
        viewModel.locationData.observeForever {
            if (it != null) {
                latch.countDown()
            }
        }

        viewModel.getLocation()

        latch.await(1, TimeUnit.SECONDS)
        assertEquals(location, viewModel.locationData.value)
    }


    @Test
    fun getLocation_securityException() = runBlocking {
        `when`(locationRepository.getLocation()).thenThrow(SecurityException())

        val latch = CountDownLatch(1)
        viewModel.searchState.observeForever {
            if (it == LocationSearchState.ERROR) {
                latch.countDown()
            }
        }

        viewModel.getLocation()

        latch.await(2, TimeUnit.SECONDS)
        assertEquals(LocationSearchState.ERROR, viewModel.searchState.value)
    }

    @Test
    fun onOpenLocationSettingClicked() = runTest {
        viewModel.onOpenLocationSettingClicked()
        assertNotNull(viewModel.openLocationSettingEvent.value)
    }

    @Test
    fun onRetryClicked() = runTest {
        viewModel.onRetryClicked()
        assertNotNull(viewModel.retryEvent.value)
    }
}