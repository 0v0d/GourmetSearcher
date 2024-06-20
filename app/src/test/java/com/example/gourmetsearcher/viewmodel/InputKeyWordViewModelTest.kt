package com.example.gourmetsearcher.viewmodel

import com.example.gourmetsearcher.usecase.keywordhistory.ClearKeyWordHistoryUseCase
import com.example.gourmetsearcher.usecase.keywordhistory.GetKeyWordHistoryUseCase
import com.example.gourmetsearcher.usecase.keywordhistory.SaveKeyWordHistoryUseCase
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/** InputKeyWordViewModelのユニットテストクラス */
@RunWith(MockitoJUnitRunner::class)
class InputKeyWordViewModelTest {
    @Mock
    private lateinit var getHistoryListUseCase: GetKeyWordHistoryUseCase

    @Mock
    private lateinit var saveHistoryItemUseCase: SaveKeyWordHistoryUseCase

    @Mock
    private lateinit var clearHistoryUseCase: ClearKeyWordHistoryUseCase

    @InjectMocks
    private lateinit var viewModel: InputKeyWordViewModel

    /** 初期化時に履歴リストが正しく読み込まれることを確認するテスト */
    @Test
    fun testLoadHistoryOnInit() {
        val initialHistory = listOf("item1", "item2")
        `when`(getHistoryListUseCase()).thenReturn(initialHistory)

        viewModel = InputKeyWordViewModel(getHistoryListUseCase, saveHistoryItemUseCase, clearHistoryUseCase)

        assertEquals(initialHistory, viewModel.historyListData.value)
    }

    /** 新しい項目を保存した後、履歴リストが更新されることを確認するテスト */
    @Test
    fun testSaveHistoryItemUpdatesHistoryList() {
        val initialHistory = listOf("item1")
        val newItem = "item2"
        val updatedHistory = listOf("item2", "item1")

        `when`(getHistoryListUseCase()).thenReturn(initialHistory, updatedHistory)

        viewModel = InputKeyWordViewModel(getHistoryListUseCase, saveHistoryItemUseCase, clearHistoryUseCase)
        viewModel.saveHistoryItem(newItem)

        verify(saveHistoryItemUseCase).invoke(newItem)
        assertEquals(updatedHistory, viewModel.historyListData.value)
    }

    /** 履歴リストの保存と取得を確認するテスト */
    @Test
    fun testSaveAndGetHistoryItem() {
        val keyword = "test"
        `when`(getHistoryListUseCase()).thenReturn(listOf(keyword))

        viewModel.saveHistoryItem(keyword)

        verify(saveHistoryItemUseCase, times(1)).invoke(keyword)
        assertEquals(listOf(keyword), viewModel.historyListData.value)
    }

    /** 履歴リストのクリアを確認するテスト */
    @Test
    fun testClearHistory() {
        viewModel.clearHistory()

        verify(clearHistoryUseCase, times(1)).invoke()
        assertEquals(emptyList<String>(), viewModel.historyListData.value)
    }
}
