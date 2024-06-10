package com.example.gourmetsearcher.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.gourmetsearcher.usecase.ClearKeyWordHistoryUseCase
import com.example.gourmetsearcher.usecase.GetKeyWordHistoryUseCase
import com.example.gourmetsearcher.usecase.SaveKeyWordHistoryUseCase
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
    private lateinit var getHistoryListUseCase: GetKeyWordHistoryUseCase

    @Mock
    private lateinit var saveHistoryItemUseCase: SaveKeyWordHistoryUseCase

    @Mock
    private lateinit var clearHistoryUseCase: ClearKeyWordHistoryUseCase

    private lateinit var viewModel: InputKeyWordViewModel

    @Before
    fun setUp() {
        viewModel =
            InputKeyWordViewModel(
                getHistoryListUseCase,
                saveHistoryItemUseCase,
                clearHistoryUseCase,
            )
    }

    /** 履歴リストの保存と取得を確認する */
    @Test
    fun testSaveAndGetHistoryItem() {
        val keyword = "test"
        `when`(getHistoryListUseCase()).thenReturn(listOf(keyword))

        viewModel.saveHistoryItem(keyword)

        verify(saveHistoryItemUseCase, times(1)).invoke(keyword)
        assertEquals(listOf(keyword), viewModel.historyListData.value)
    }

    /** 履歴リストのクリアを確認する */
    @Test
    fun testClearHistory() {
        viewModel.clearHistory()

        verify(clearHistoryUseCase, times(1)).invoke()
        assertEquals(emptyList<String>(), viewModel.historyListData.value)
    }
}
