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

    /** 位置情報の取得時にエラーが発生した場合のテスト */
    @Test
    fun testGetLocationError() =
        runTest {
            `when`(getCurrentLocationUseCase()).thenThrow(RuntimeException())

            viewModel.getLocation()

            assertEquals(LocationSearchState.ERROR, viewModel.searchState.value)
        }

    /** 位置情報の取得が成功した場合のテスト */
    @Test
    fun testPerformSearch() =
        runTest {
            val mockLocation = mock<Location>()
            `when`(mockLocation.latitude).thenReturn(35.0)
            `when`(mockLocation.longitude).thenReturn(135.0)
            `when`(getCurrentLocationUseCase()).thenReturn(mockLocation)

            viewModel.getLocation()

            val expectedLocation = CurrentLocation(35.0, 135.0)
            assertEquals(expectedLocation, viewModel.locationData.value)
        }

    /** 検索状態の設定テスト */
    @Test
    fun testSetSearchState() {
        viewModel.setSearchState(LocationSearchState.ERROR)
        assertEquals(LocationSearchState.ERROR, viewModel.searchState.value)
    }

    /** 位置情報の取得に成功した場合のテスト */
    @Test
    fun testGetLocationSuccess() =
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
    fun testGetLocationSecurityException() =
        runBlocking {
            `when`(getCurrentLocationUseCase()).thenThrow(SecurityException())

            val latch = CountDownLatch(1)

            viewModel.getLocation()

            latch.await(2, TimeUnit.SECONDS)
            assertEquals(LocationSearchState.ERROR, viewModel.searchState.value)
        }

    /** nullポインタ例外が発生した場合のテスト */
    @Test
    fun testGetLocationNullPointerException() =
        runBlocking {
            `when`(getCurrentLocationUseCase()).thenThrow(NullPointerException())

            val latch = CountDownLatch(1)

            viewModel.getLocation()

            latch.await(2, TimeUnit.SECONDS)
            assertEquals(LocationSearchState.ERROR, viewModel.searchState.value)
        }

    /** 設定ボタンを押した時のテスト */
    @Test
    fun testOnOpenLocationSettingClicked() =
        runTest {
            viewModel.onOpenLocationSettingClicked()
        }

    /** リトライボタンを押した時のテスト */
    @Test
    fun testOnRetryClicked() =
        runTest {
            viewModel.onRetryClicked()
        }
}
