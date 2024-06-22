package com.example.gourmetsearcher.usecase.keywordhistory

import com.example.gourmetsearcher.repository.KeyWordHistoryRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

/** SaveKeyWordHistoryUseCaseのユニットテストクラス */
@RunWith(MockitoJUnitRunner::class)
class SaveKeyWordHistoryUseCaseTest {
    @Mock
    private lateinit var keyWordHistoryRepository: KeyWordHistoryRepository

    @InjectMocks
    private lateinit var saveKeyWordHistoryUseCase: SaveKeyWordHistoryUseCase

    /** invokeが正しく呼び出されるかテスト */
    @Test
    fun testInvokeCallsSaveHistoryItem() {
        val keyword = "testKeyword"

        saveKeyWordHistoryUseCase(keyword)

        verify(keyWordHistoryRepository).saveHistoryItem(keyword)
    }
}
