package com.example.gourmetsearcher.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.gourmetsearcher.model.BudgetData
import com.example.gourmetsearcher.model.CurrentLocation
import com.example.gourmetsearcher.model.GenreData
import com.example.gourmetsearcher.model.HotPepperResponse
import com.example.gourmetsearcher.model.LargeAreaData
import com.example.gourmetsearcher.model.PCData
import com.example.gourmetsearcher.model.PhotoData
import com.example.gourmetsearcher.model.RestaurantData
import com.example.gourmetsearcher.model.Results
import com.example.gourmetsearcher.model.SearchTerms
import com.example.gourmetsearcher.model.SmallAreaData
import com.example.gourmetsearcher.model.Urls
import com.example.gourmetsearcher.repository.HotPepperRepository
import com.example.gourmetsearcher.state.SearchState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class RestaurantListViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: HotPepperRepository

    @Mock
    private lateinit var restaurantDataObserver: Observer<List<RestaurantData>>

    @Mock
    private lateinit var searchStateObserver: Observer<SearchState>
    private lateinit var viewModel: RestaurantListViewModel

    private val response = HotPepperResponse(
        Results(
            listOf(
                RestaurantData(
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
                    "Close"
                )
            )
        )
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = RestaurantListViewModel(repository)
        viewModel.restaurantData.observeForever(restaurantDataObserver)
        viewModel.searchState.observeForever(searchStateObserver)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }

    /**
     * ホットペッパーグルメAPIからのレスポンスが成功した場合のテスト
     * 検索状態がLOADINGからDONEに変わること
     * レストラン情報が更新されること
     * 検索状態がDONEに変わること
     */
    @Test
    fun searchHotPepperRepositorySuccess() =
        runTest {
            val searchTerms = SearchTerms("keyword", CurrentLocation(0.0, 0.0), 10)
            `when`(repository.searchHotPepperRepository(searchTerms))
                .thenReturn(Response.success(response))
            viewModel.searchRestaurants(searchTerms)
            verify(searchStateObserver).onChanged(SearchState.LOADING)
            verify(restaurantDataObserver).onChanged(response.results.shops)
            verify(searchStateObserver).onChanged(SearchState.DONE)
        }

    /**
     * ホットペッパーグルメAPIからのレスポンスが失敗した場合のテスト
     * 検索状態がLOADINGからNETWORK_ERRORに変わること
     * レストラン情報が更新されること
     * 検索状態がNETWORK_ERRORに変わること
     */
    @Test
    fun searchHotPepperRepositoryNetWorkErrorResponse() =
        runTest {
            val searchTerms = SearchTerms("keyword", CurrentLocation(0.0, 0.0), 10)
            val response = Response.error<HotPepperResponse>(500, ResponseBody.create(null, ""))
            `when`(repository.searchHotPepperRepository(searchTerms))
                .thenReturn(response)
            viewModel.searchRestaurants(searchTerms)
            verify(searchStateObserver).onChanged(SearchState.LOADING)
            verify(restaurantDataObserver).onChanged(response.body()?.results?.shops ?: emptyList())
            verify(searchStateObserver).onChanged(SearchState.NETWORK_ERROR)
        }

    /**
     * ホットペッパーグルメAPIからのレスポンスが空の場合のテスト
     * 検索状態がLOADINGからEMPTY_RESULTに変わること
     * レストラン情報が更新されること
     * 検索状態がEMPTY_RESULTに変わること
     */
    @Test
    fun searchHotPepperRepositoryEmptyResponse() =
        runTest {
            val searchTerms = SearchTerms("keyword", CurrentLocation(0.0, 0.0), 10)
            val response = HotPepperResponse(Results(emptyList()))
            `when`(repository.searchHotPepperRepository(searchTerms))
                .thenReturn(Response.success(response))
            viewModel.searchRestaurants(searchTerms)
            verify(searchStateObserver).onChanged(SearchState.LOADING)
            verify(restaurantDataObserver).onChanged(response.results.shops)
            verify(searchStateObserver).onChanged(SearchState.EMPTY_RESULT)
        }

    /**
     * ホットペッパーグルメAPIからのレスポンスがnullの場合のテスト
     */
    @Test
    fun searchHotPepperRepositoryRetrySearch() =
        runTest {
            val searchTerms = SearchTerms("keyword", CurrentLocation(0.0, 0.0), 10)
            val response = Response.success(HotPepperResponse(Results(emptyList())))
            `when`(repository.searchHotPepperRepository(searchTerms))
                .thenReturn(response)
            viewModel.searchRestaurants(searchTerms)
            assertEquals(response.body()?.results?.shops, viewModel.restaurantData.value)
            viewModel.retrySearch()
            assertEquals(response.body()?.results?.shops, viewModel.restaurantData.value)
        }
}