package com.example.gourmetsearcher.repository

import com.example.gourmetsearcher.BuildConfig
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
import com.example.gourmetsearcher.source.HotPepperNetworkDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyDouble
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class HotPepperRepositoryImplTest {

    private lateinit var repository: HotPepperRepositoryImpl
    private val mockService = mock(HotPepperNetworkDataSource::class.java)
    private val mockCacheManager = mock(CacheManager::class.java)
    private val searchTerms = SearchTerms("keyword", CurrentLocation(35.0, 139.0), 1)
    private val mockResponse = Response.success(
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
                        "Close"
                    )
                )
            )
        )
    )


    @Before
    fun setUp() {
        repository = HotPepperRepositoryImpl(mockService, mockCacheManager)
    }

    @Test
    fun `execute returns cached response if available`() = runBlocking {
        `when`(mockCacheManager.get(searchTerms)).thenReturn(mockResponse)

        val result = repository.execute(searchTerms)

        verify(mockCacheManager).get(searchTerms)
        verify(mockService, never()).getRestaurantDatum(
            anyString(),
            anyString(),
            anyDouble(),
            anyDouble(),
            anyInt(),
            anyString()
        )
        assertEquals(mockResponse, result)
    }

    @Test
    fun `execute fetches from service and caches response if not cached`() = runBlocking {
        `when`(mockCacheManager.get(searchTerms)).thenReturn(null)
        `when`(
            mockService.getRestaurantDatum(
                anyString(),
                anyString(),
                anyDouble(),
                anyDouble(),
                anyInt(),
                anyString()
            )
        ).thenReturn(mockResponse)

        val result = repository.execute(searchTerms)

        verify(mockCacheManager).get(searchTerms)
        verify(mockService).getRestaurantDatum(
            BuildConfig.API_KEY,
            searchTerms.keyword,
            searchTerms.location.lat,
            searchTerms.location.lng,
            searchTerms.range,
            "json"
        )
        verify(mockCacheManager).put(searchTerms, mockResponse)
        assertEquals(mockResponse, result)
    }

    @Test
    fun `execute returns null on service exception`() = runBlocking {
        `when`(
            mockService.getRestaurantDatum(
                anyString(),
                anyString(),
                anyDouble(),
                anyDouble(),
                anyInt(),
                anyString()
            )
        ).thenThrow(RuntimeException::class.java)

        val result = repository.execute(searchTerms)
        
        assertNull(result)
    }
}
