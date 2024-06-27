package com.example.gourmetsearcher.repository

import com.example.gourmetsearcher.manager.PreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/** KeyWordHistoryRepositoryImplのユニットテストクラス */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class KeyWordHistoryRepositoryImplTest {
    @Mock
    private lateinit var preferences: PreferencesManager

    private lateinit var keyWordHistoryRepository: KeyWordHistoryRepository
    private val testDispatcher = UnconfinedTestDispatcher()

    /** 各テスト前の準備 */

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        keyWordHistoryRepository = KeyWordHistoryRepositoryImpl(preferences)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /** saveHistoryItemが正しく呼び出されるかテスト */
    @Test
    fun testSaveHistoryItem() =
        runTest {
            val input = "keyword 1"
            keyWordHistoryRepository.saveHistoryItem(input)
            verify(preferences).saveHistoryItem(input)
        }

    /** getHistoryListが期待通りのリストを返すかテスト */
    @Test
    fun testGetHistoryList() =
        runTest {
            val expectedHistoryList = listOf("keyword1", "keyword2", "keyword3")
            `when`(preferences.getHistoryList()).thenReturn(flowOf(expectedHistoryList))
            val actualHistoryList = keyWordHistoryRepository.getHistoryList()
            actualHistoryList.collect {
                assertEquals(expectedHistoryList, it)
            }
        }

    /** clearHistoryが正しく呼び出されるかテスト */
    @Test
    fun testClearHistory() =
        runTest {
            keyWordHistoryRepository.clearHistory()
            verify(preferences).clearHistory()
        }

    /** 空文字列の入力が無視されるかテスト */
    @Test
    fun testEmptyInput() =
        runTest {
            val input = ""
            keyWordHistoryRepository.saveHistoryItem(input)
            verify(preferences, never()).saveHistoryItem(input)
        }

    /** 空白文字列の入力が無視されるかテスト */
    @Test
    fun testBlankInput() =
        runTest {
            val input = "  "
            keyWordHistoryRepository.saveHistoryItem(input)
            verify(preferences, never()).saveHistoryItem(input)
        }
}
