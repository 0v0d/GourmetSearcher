package com.example.gourmetsearcher.usecase.network

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
import com.example.gourmetsearcher.repository.HotPepperRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

/** GetHotPepperDataUseCaseのユニットテストクラス */
@RunWith(MockitoJUnitRunner::class)
class GetHotPepperDataUseCaseTest {
    @Mock
    private lateinit var hotPepperRepository: HotPepperRepository

    @InjectMocks
    private lateinit var getHotPepperDataUseCase: GetHotPepperDataUseCase

    private val mockResponse =
        Response.success(
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
            ),
        )

    private val mockSearchTerms = SearchTerms("", CurrentLocation(0.0, 0.0), 1)

    /** 正しくAPIが呼び出された場合のテスト */
    @Test
    fun testInvokeReturnsSuccessful() =
        runBlocking {
            `when`(hotPepperRepository.execute(mockSearchTerms))
                .thenReturn(mockResponse)

            val result = getHotPepperDataUseCase(mockSearchTerms)

            assertEquals(mockResponse, result)
        }

    /** レスポンスがnullの場合のテスト */
    @Test
    fun testInvokeReturnsNull() =
        runBlocking {
            `when`(hotPepperRepository.execute(mockSearchTerms))
                .thenReturn(null)

            val result = getHotPepperDataUseCase(mockSearchTerms)

            assertNull(result)
        }

    /** APIエラー時のテスト */
    @Test
    fun testInvokeReturnsError() =
        runBlocking {
            `when`(hotPepperRepository.execute(mockSearchTerms))
                .thenThrow(RuntimeException("API Error"))

            try {
                getHotPepperDataUseCase(mockSearchTerms)
                assert(false)
            } catch (e: RuntimeException) {
                assert(e.message == "API Error")
            }
        }
}
