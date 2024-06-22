package com.example.gourmetsearcher.usecase.keywordhistory

import com.example.gourmetsearcher.repository.KeyWordHistoryRepository
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/** GetKeyWordHistoryUseCaseのユニットテストクラス */
@RunWith(MockitoJUnitRunner::class)
class GetKeyWordHistoryUseCaseTest {
    @Mock
    private lateinit var keyWordHistoryRepository: KeyWordHistoryRepository

    @InjectMocks
    private lateinit var getKeyWordHistoryUseCase: GetKeyWordHistoryUseCase

    /** invokeが履歴リストを返すかテスト */
    @Test
    fun testInvokeReturnsHistoryList() {
        val expectedHistoryList = listOf("keyword1", "keyword2", "keyword3")
        `when`(keyWordHistoryRepository.getHistoryList()).thenReturn(expectedHistoryList)

        val result = getKeyWordHistoryUseCase()

        assertEquals(expectedHistoryList, result)
    }
}
