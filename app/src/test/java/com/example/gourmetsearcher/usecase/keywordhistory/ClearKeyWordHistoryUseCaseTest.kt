package com.example.gourmetsearcher.usecase.keywordhistory

import com.example.gourmetsearcher.repository.KeyWordHistoryRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

/** ClearKeyWordHistoryUseCaseのユニットテストクラス */
@RunWith(MockitoJUnitRunner::class)
class ClearKeyWordHistoryUseCaseTest {
    @Mock
    private lateinit var keyWordHistoryRepository: KeyWordHistoryRepository

    @InjectMocks
    private lateinit var clearKeyWordHistoryUseCase: ClearKeyWordHistoryUseCase

    /** invokeが正しく呼び出されるかテスト */
    @Test
    fun testInvokeCallsClearHistory() {
        clearKeyWordHistoryUseCase()

        verify(keyWordHistoryRepository).clearHistory()
    }
}
