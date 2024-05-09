package com.example.gourmetsearcher.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RestaurantListViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: HotPepperRepository

    private lateinit var viewModel: RestaurantListViewModel

    private val response = HotPepperResponse(
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
                    "Close"
                )
            )
        )
    )
    private val emptyResponse = HotPepperResponse(
        Results(
            emptyList()
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = RestaurantListViewModel(repository)
    }

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
            `when`(repository.execute(searchTerms)).thenReturn(Response.success(response))
            viewModel.searchRestaurants(searchTerms)

            verify(repository).execute(searchTerms)
            val shops = response.results.shops.map { it.toDomain() }
            assertEquals(shops, viewModel.shops.value)
            assertEquals(SearchState.SUCCESS, viewModel.searchState.value)
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
            `when`(repository.execute(searchTerms))
                .thenReturn(response)
            viewModel.searchRestaurants(searchTerms)

            assertEquals(null, viewModel.shops.value)
            assertEquals(SearchState.NETWORK_ERROR, viewModel.searchState.value)
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
            val searchTerms = SearchTerms("keyword", CurrentLocation(0.0, 0.0), 1)
            `when`(repository.execute(searchTerms)).thenReturn(Response.success(emptyResponse))

            viewModel.searchRestaurants(searchTerms)
            val shops = emptyResponse.results.shops.map { it.toDomain() }
            assertEquals(emptyList<Shops>(), shops)
            assertEquals(SearchState.EMPTY_RESULT, viewModel.searchState.value)
        }
}