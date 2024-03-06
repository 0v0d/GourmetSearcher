package com.example.gourmetsearcher.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.gourmetsearcher.repository.KeyWordHistoryRepository
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class InputKeyWordViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var mockRepository: KeyWordHistoryRepository

    @Mock
    private lateinit var observer: Observer<List<String>>

    private lateinit var viewModel: InputKeyWordViewModel

    @Before
    fun setUp() {
        viewModel = InputKeyWordViewModel(mockRepository)
        viewModel.historyList.observeForever(observer)
    }

    @After
    fun cleanup() {
        viewModel.historyList.removeObserver(observer)
    }

    /** 入力が空でないことを確認する */
    @Test
    fun testIsNotInputEmpty() {
        assert(viewModel.isNotInputEmpty("test"))
        assert(!viewModel.isNotInputEmpty(""))
    }

    /** 履歴リストの保存と取得を確認する */
    @Test
    fun testSaveAndGetHistoryItem() {
        val testItem = "test"
        `when`(mockRepository.getHistoryList()).thenReturn(listOf(testItem))

        viewModel.saveHistoryItem(testItem)

        verify(mockRepository, times(1)).saveHistoryItem(testItem)
        verify(mockRepository, times(2)).getHistoryList()

        val historyList = viewModel.historyList.value
        assertTrue(historyList?.contains(testItem) ?: false)
    }

    /** 履歴リストのクリアを確認する */
    @Test
    fun testClearHistory() {
        viewModel.clearHistory()
        verify(mockRepository, times(1)).clearHistory()
        verify(mockRepository, times(2)).getHistoryList()
    }
}