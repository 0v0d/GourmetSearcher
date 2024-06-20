package com.example.gourmetsearcher.viewmodel

import com.example.gourmetsearcher.model.api.BudgetData
import com.example.gourmetsearcher.model.api.GenreData
import com.example.gourmetsearcher.model.api.HotPepperResponse
import com.example.gourmetsearcher.model.api.LargeAreaData
import com.example.gourmetsearcher.model.api.PCData
import com.example.gourmetsearcher.model.api.PhotoData
import com.example.gourmetsearcher.model.api.Results
import com.example.gourmetsearcher.model.api.Shops
import com.example.gourmetsearcher.model.api.SmallAreaData
import com.example.gourmetsearcher.model.api.Urls
import com.example.gourmetsearcher.model.data.CurrentLocation
import com.example.gourmetsearcher.model.data.SearchTerms
import com.example.gourmetsearcher.model.domain.toDomain
import com.example.gourmetsearcher.state.SearchState
import com.example.gourmetsearcher.usecase.network.GetHotPepperDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

/** RestaurantListViewModelのユニットテストクラス */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RestaurantListViewModelTest {
    @Mock
    private lateinit var getHotPepperDataUseCase: GetHotPepperDataUseCase

    @InjectMocks
    private lateinit var viewModel: RestaurantListViewModel

    private val response =
        HotPepperResponse(
            Results(
                listOf(
                    Shops(
                        "1",
                        "Restaurant",
                        "Address",
                        "Station",
                        LargeAreaData("Large Area"),
                        SmallAreaData("Small Area"),
                        GenreData("Genre"),
                        BudgetData("Budget"),
                        "Access",
                        Urls("URL"),
                        PhotoData(PCData("Photo URL")),
                        "Open",
                        "Close",
                    ),
                ),
            ),
        )
    private val emptyResponse =
        HotPepperResponse(
            Results(
                emptyList(),
            ),
        )

    /** 各テスト前の準備 */
    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    /** 各テスト後のクリーンアップ */
    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }

    /** ホットペッパーグルメAPIからのレスポンスが成功した場合のテスト */
    @Test
    fun testSearchRestaurantsSuccess() =
        runTest {
            val searchTerms = SearchTerms("keyword", CurrentLocation(0.0, 0.0), 10)
            `when`(getHotPepperDataUseCase(searchTerms)).thenReturn(Response.success(response))
            viewModel.searchRestaurants(searchTerms)

            val shops = response.results.shops.map { it.toDomain() }
            assertEquals(shops, viewModel.shops.value)
            assertEquals(SearchState.SUCCESS, viewModel.searchState.value)
        }

    /** ホットペッパーグルメAPIからのレスポンスが失敗した場合のテスト */
    @Test
    fun testSearchRestaurantsNetworkError() =
        runTest {
            val searchTerms = SearchTerms("keyword", CurrentLocation(0.0, 0.0), 1)
            `when`(getHotPepperDataUseCase(searchTerms)).thenReturn(null)

            viewModel.searchRestaurants(searchTerms)

            assertEquals(SearchState.NETWORK_ERROR, viewModel.searchState.value)
        }

    /** ホットペッパーグルメAPIからのレスポンスが空の場合のテスト */
    @Test
    fun testSearchRestaurantsEmptyResponse() =
        runTest {
            val searchTerms = SearchTerms("keyword", CurrentLocation(0.0, 0.0), 1)
            `when`(getHotPepperDataUseCase(searchTerms)).thenReturn(Response.success(emptyResponse))

            viewModel.searchRestaurants(searchTerms)
            val shops = emptyResponse.results.shops.map { it.toDomain() }
            assertEquals(emptyList<Shops>(), shops)
            assertEquals(SearchState.EMPTY_RESULT, viewModel.searchState.value)
        }

    /** 検索のリトライテスト */
    @Test
    fun testRetrySearch() =
        runTest {
            val searchTerms = SearchTerms("keyword", CurrentLocation(0.0, 0.0), 1)
            val mockResponse = mock<Response<HotPepperResponse>>()
            `when`(getHotPepperDataUseCase(searchTerms)).thenReturn(mockResponse)

            viewModel.searchRestaurants(searchTerms)
            viewModel.retrySearch()

            verify(getHotPepperDataUseCase, times(2)).invoke(searchTerms)
        }

    /** 空のキーワードでの検索リトライテスト */
    @Test
    fun testRetrySearchWithEmptyKeyword() =
        runTest {
            val searchTerms = SearchTerms("", CurrentLocation(0.0, 0.0), 1)
            viewModel.searchRestaurants(searchTerms)
            viewModel.retrySearch()

            verify(getHotPepperDataUseCase, times(1)).invoke(searchTerms)
        }
}
