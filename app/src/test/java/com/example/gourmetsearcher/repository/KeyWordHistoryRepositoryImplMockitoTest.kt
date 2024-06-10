package com.example.gourmetsearcher.repository

import com.example.gourmetsearcher.manager.PreferencesManager
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class KeyWordHistoryRepositoryImplTest {
    @Mock
    private lateinit var preferences: PreferencesManager

    @InjectMocks
    private lateinit var keyWordHistoryRepository: KeyWordHistoryRepositoryImpl

    /** saveHistoryItemが呼ばれたときに、PreferencesManagerのsaveHistoryItemが呼ばれることを確認するテスト */
    @Test
    fun testSaveHistoryItemWithNewEntry() {
        val input = "keyword1"
        keyWordHistoryRepository.saveHistoryItem(input)
        verify(preferences).saveHistoryItem(input)
    }

    /** getHistoryListが呼ばれたときに、PreferencesManagerのgetHistoryListが呼ばれ、期待されるリストが返されることを確認するテスト */
    @Test
    fun testGetHistoryList() {
        val expectedHistoryList = listOf("keyword1", "keyword2", "keyword3")
        `when`(preferences.getHistoryList()).thenReturn(expectedHistoryList)

        val actualHistoryList = keyWordHistoryRepository.getHistoryList()

        assertEquals(expectedHistoryList, actualHistoryList)
        verify(preferences).getHistoryList()
    }

    /** clearHistoryが呼ばれたときに、PreferencesManagerのclearHistoryが呼ばれることを確認するテスト */
    @Test
    fun testClearHistory() {
        keyWordHistoryRepository.clearHistory()
        verify(preferences).clearHistory()
    }
}
