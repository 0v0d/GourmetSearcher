package com.example.gourmetsearcher.viewmodel

import com.example.gourmetsearcher.usecase.keywordhistory.ClearKeyWordHistoryUseCase
import com.example.gourmetsearcher.usecase.keywordhistory.GetKeyWordHistoryUseCase
import com.example.gourmetsearcher.usecase.keywordhistory.SaveKeyWordHistoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
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
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/** InputKeyWordViewModelのユニットテストクラス */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class InputKeyWordViewModelTest {
    @Mock
    private lateinit var getHistoryListUseCase: GetKeyWordHistoryUseCase

    @Mock
    private lateinit var saveHistoryItemUseCase: SaveKeyWordHistoryUseCase

    @Mock
    private lateinit var clearHistoryUseCase: ClearKeyWordHistoryUseCase

    private lateinit var viewModel: InputKeyWordViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    /** 各テスト前の準備 */
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel =
            InputKeyWordViewModel(
                getHistoryListUseCase,
                saveHistoryItemUseCase,
                clearHistoryUseCase,
            )
    }

    /** 各テスト後のCleanup */
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /** 初期化時に履歴リストが正しく読み込まれることを確認するテスト */
    @Test
    fun testLoadHistoryOnInit() =
        runBlocking {
            val initialHistory = listOf("apple", "banana")
            `when`(getHistoryListUseCase()).thenReturn(flowOf(initialHistory))

            viewModel =
                InputKeyWordViewModel(
                    getHistoryListUseCase,
                    saveHistoryItemUseCase,
                    clearHistoryUseCase,
                )

            assertEquals(viewModel.historyListData.value, initialHistory)
        }

    /** 新しい項目を保存した後、履歴リストが更新されることを確認するテスト */
    @Test
    fun testSaveHistoryItemUpdatesHistoryList() =
        runTest {
            val initialHistory = listOf("apple", "banana")
            val newItem = "cherry"
            val updatedHistory = listOf("apple", "banana", "cherry")

            `when`(getHistoryListUseCase()).thenReturn(flowOf(initialHistory, updatedHistory))

            viewModel =
                InputKeyWordViewModel(
                    getHistoryListUseCase,
                    saveHistoryItemUseCase,
                    clearHistoryUseCase,
                )
            viewModel.saveHistoryItem(newItem)

            verify(saveHistoryItemUseCase).invoke(newItem)
            assertEquals(viewModel.historyListData.value, updatedHistory)
        }

    /** 履歴リストの保存と取得を確認するテスト */
    @Test
    fun testSaveAndGetHistoryItem() =
        runBlocking {
            val keyword = "test"
            `when`(getHistoryListUseCase()).thenReturn(flowOf(listOf(keyword)))

            viewModel =
                InputKeyWordViewModel(
                    getHistoryListUseCase,
                    saveHistoryItemUseCase,
                    clearHistoryUseCase,
                )
            viewModel.saveHistoryItem(keyword)

            verify(saveHistoryItemUseCase).invoke(keyword)
            val historyList = viewModel.historyListData.value
            assertEquals(listOf(keyword), historyList)
        }

    /** 履歴リストのクリアを確認するテスト */
    @Test
    fun testClearHistory() =
        runBlocking {
            `when`(getHistoryListUseCase()).thenReturn(flowOf(emptyList()))

            viewModel =
                InputKeyWordViewModel(
                    getHistoryListUseCase,
                    saveHistoryItemUseCase,
                    clearHistoryUseCase,
                )
            viewModel.clearHistory()

            verify(clearHistoryUseCase).invoke()
            assertEquals(viewModel.historyListData.value, emptyList<String>())
        }
}
