package com.example.gourmetsearcher.repository

import com.example.gourmetsearcher.manager.PreferencesManager
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/** KeyWordHistoryRepositoryImplのユニットテストクラス */
@RunWith(MockitoJUnitRunner::class)
class KeyWordHistoryRepositoryImplTest {
    @Mock
    private lateinit var preferences: PreferencesManager

    private lateinit var keyWordHistoryRepository: KeyWordHistoryRepository

    /** 各テスト前の準備 */
    @Before
    fun setUp() {
        keyWordHistoryRepository = KeyWordHistoryRepositoryImpl(preferences)
    }

    /** saveHistoryItemが正しく呼び出されるかテスト */
    @Test
    fun testSaveHistoryItem() {
        val input = "keyword1"
        keyWordHistoryRepository.saveHistoryItem(input)
        verify(preferences).saveHistoryItem(input)
    }

    /** getHistoryListが期待通りのリストを返すかテスト */
    @Test
    fun testGetHistoryList() {
        val expectedHistoryList = listOf("keyword1", "keyword2", "keyword3")
        `when`(preferences.getHistoryList()).thenReturn(expectedHistoryList)
        val actualHistoryList = keyWordHistoryRepository.getHistoryList()
        assertEquals(expectedHistoryList, actualHistoryList)
        verify(preferences).getHistoryList()
    }

    /** clearHistoryが正しく呼び出されるかテスト */
    @Test
    fun testClearHistory() {
        keyWordHistoryRepository.clearHistory()
        verify(preferences).clearHistory()
    }
}
