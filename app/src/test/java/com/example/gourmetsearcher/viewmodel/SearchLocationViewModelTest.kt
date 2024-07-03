package com.example.gourmetsearcher.viewmodel

import android.location.Location
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
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/** SearchLocationViewModelのユニットテストクラス */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SearchLocationViewModelTest {
    @Mock
    private lateinit var getCurrentLocationUseCase: GetCurrentLocationUseCase

    @InjectMocks
    private lateinit var viewModel: SearchLocationViewModel

    /** 各テスト前の準備 */
    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    /** 各テスト後のクリーンアップ */
    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }

    /** 位置情報の取得が成功した場合のテスト */
    @Test
    fun testGetLocationSuccess() =
        runTest {
            val expectedLocation = CurrentLocation(34.7010289, 135.4955003)
            val mockLocation = mock<Location>()
            `when`(mockLocation.latitude).thenReturn(expectedLocation.lat)
            `when`(mockLocation.longitude).thenReturn(expectedLocation.lng)
            `when`(getCurrentLocationUseCase()).thenReturn(mockLocation)

            viewModel.handlePermissionGranted()

            assertEquals(expectedLocation, viewModel.locationData.value)
        }

    /** ランタイムエラーが発生した場合のテスト */
    @Test
    fun testGetLocationError() =
        runTest {
            `when`(getCurrentLocationUseCase()).thenThrow(RuntimeException())

            viewModel.handlePermissionGranted()

            assertEquals(LocationSearchState.Error, viewModel.locationSearchState.value)
        }

    /** nullポインタ例外が発生した場合のテスト */
    @Test
    fun testGetLocationNullPointerException() =
        runBlocking {
            `when`(getCurrentLocationUseCase()).thenThrow(NullPointerException())

            val latch = CountDownLatch(1)

            viewModel.handlePermissionGranted()

            latch.await(2, TimeUnit.SECONDS)
            assertEquals(LocationSearchState.Error, viewModel.locationSearchState.value)
        }

    /** セキュリティ例外が発生した場合のテスト */
    @Test
    fun testGetLocationSecurityException() =
        runBlocking {
            `when`(getCurrentLocationUseCase()).thenThrow(SecurityException())

            val latch = CountDownLatch(1)

            viewModel.handlePermissionGranted()

            latch.await(2, TimeUnit.SECONDS)
            assertEquals(LocationSearchState.Error, viewModel.locationSearchState.value)
        }

    /** 拒否された場合のテスト */
    @Test
    fun testHandlePermissionDenied() {
        viewModel.handlePermissionDenied()
        assertEquals(LocationSearchState.Error, viewModel.locationSearchState.value)
    }

    /** パーミッションの許可が必要な場合のテスト */
    @Test
    fun testHandleRationaleRequired() {
        viewModel.handleRationaleRequired()
        assertEquals(LocationSearchState.RationalRequired, viewModel.locationSearchState.value)
    }
}
