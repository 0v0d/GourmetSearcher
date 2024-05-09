package com.example.gourmetsearcher.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.gourmetsearcher.repository.KeyWordHistoryRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class InputKeyWordViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockRepository: KeyWordHistoryRepository

    private lateinit var viewModel: InputKeyWordViewModel

    @Before
    fun setUp() {
        viewModel = InputKeyWordViewModel(mockRepository)
    }

    /** 履歴リストの保存と取得を確認する */
    @Test
    fun testSaveAndGetHistoryItem() {
        val keyword = "test"
        `when`(mockRepository.getHistoryList()).thenReturn(listOf(keyword))

        viewModel.saveHistoryItem(keyword)

        verify(mockRepository, times(1)).saveHistoryItem(keyword)
        assertEquals(listOf(keyword), viewModel.historyListData.value)
    }


    /** 履歴リストのクリアを確認する */
    @Test
    fun testClearHistory() {
        viewModel.clearHistory()

        verify(mockRepository, times(1)).clearHistory()

        assertEquals(emptyList<String>(), viewModel.historyListData.value)
    }
}