package com.example.gourmetsearcher.repository

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
    
    @Test
    fun testSaveHistoryItemWithNewEntry() {
        val input = "keyword1"
        keyWordHistoryRepository.saveHistoryItem(input)
        verify(preferences).saveHistoryItem(input)
    }

    @Test
    fun testGetHistoryList() {
        val expectedHistoryList = listOf("keyword1", "keyword2", "keyword3")
        `when`(preferences.getHistoryList()).thenReturn(expectedHistoryList)

        val actualHistoryList = keyWordHistoryRepository.getHistoryList()

        assertEquals(expectedHistoryList, actualHistoryList)
        verify(preferences).getHistoryList()
    }

    @Test
    fun testClearHistory() {
        keyWordHistoryRepository.clearHistory()
        verify(preferences).clearHistory()
    }
}
