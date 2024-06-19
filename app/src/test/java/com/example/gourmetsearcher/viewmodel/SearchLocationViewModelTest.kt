package com.example.gourmetsearcher.viewmodel

import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.gourmetsearcher.model.data.CurrentLocation
import com.example.gourmetsearcher.state.LocationSearchState
import com.example.gourmetsearcher.usecase.location.GetCurrentLocationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SearchLocationViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getCurrentLocationUseCase: GetCurrentLocationUseCase

    private lateinit var viewModel: SearchLocationViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = SearchLocationViewModel(getCurrentLocationUseCase)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }

    /** 位置情報の取得に成功した場合のテスト */
    @Test
    fun getLocation_success() =
        runTest {
            val location = CurrentLocation(34.7010289, 135.4955003)
            val mockLocation = mock(Location::class.java)
            `when`(mockLocation.latitude).thenReturn(location.lat)
            `when`(mockLocation.longitude).thenReturn(location.lng)
            `when`(getCurrentLocationUseCase()).thenReturn(mockLocation)

            val latch = CountDownLatch(1)

            viewModel.getLocation()

            latch.await(1, TimeUnit.SECONDS)
            assertEquals(location, viewModel.locationData.value)
        }

    /** セキュリティ例外が発生した場合のテスト */
    @Test
    fun getLocation_securityException() =
        runBlocking {
            `when`(getCurrentLocationUseCase()).thenThrow(SecurityException())

            val latch = CountDownLatch(1)

            viewModel.getLocation()

            latch.await(2, TimeUnit.SECONDS)
            assertEquals(LocationSearchState.ERROR, viewModel.searchState.value)
        }

    /** nullポインタ例外が発生した場合のテスト */
    @Test
    fun getLocation_nullPointerException() =
        runBlocking {
            `when`(getCurrentLocationUseCase()).thenThrow(NullPointerException())

            val latch = CountDownLatch(1)

            viewModel.getLocation()

            latch.await(2, TimeUnit.SECONDS)
            assertEquals(LocationSearchState.ERROR, viewModel.searchState.value)
        }

    /** 設定ボタンを押した時のテスト */
    @Test
    fun onOpenLocationSettingClicked() =
        runTest {
            viewModel.onOpenLocationSettingClicked()
        }

    /** リトライボタンを押した時のテスト */
    @Test
    fun onRetryClicked() =
        runTest {
            viewModel.onRetryClicked()
        }
}
