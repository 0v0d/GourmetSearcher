package com.example.gourmetsearcher.viewmodel

import com.example.gourmetsearcher.usecase.keywordhistory.ClearKeyWordHistoryUseCase
import com.example.gourmetsearcher.usecase.keywordhistory.GetKeyWordHistoryUseCase
import com.example.gourmetsearcher.usecase.keywordhistory.SaveKeyWordHistoryUseCase
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
import org.mockito.Mockito.anyString
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

    /** 各テスト前の準備 */
    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    /** 各テスト後のCleanup */
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /** 初期化時に履歴リストが正しく読み込まれることを確認するテスト */
    @Test
    fun testLoadHistoryOnInit() =
        runTest {
            val testHistory = listOf("test1", "test2")
            `when`(getHistoryListUseCase()).thenReturn(flowOf(testHistory))

            viewModel =
                InputKeyWordViewModel(
                    getHistoryListUseCase,
                    saveHistoryItemUseCase,
                    clearHistoryUseCase,
                )

            assertEquals(testHistory, viewModel.historyListData.value)
        }

    /** 新しい項目を保存した後、履歴リストが更新されることを確認するテスト */
    @Test
    fun testSaveHistoryItemUpdatesHistoryList() =
        runTest {
            val updatedHistory = listOf("test1", "test2", "test3")

            `when`(getHistoryListUseCase()).thenReturn(
                flowOf(updatedHistory),
            )
            `when`(saveHistoryItemUseCase(anyString())).thenReturn(Unit)

            viewModel =
                InputKeyWordViewModel(
                    getHistoryListUseCase,
                    saveHistoryItemUseCase,
                    clearHistoryUseCase,
                )

            viewModel.saveHistoryItem("test3")

            verify(saveHistoryItemUseCase).invoke("test3")
            assertEquals(updatedHistory, viewModel.historyListData.value)
        }

    /** 履歴リストの保存と取得を確認するテスト */
    @Test
    fun testSaveAndGetHistoryItem() =
        runTest {
            // Given
            val testHistory = listOf("test1", "test2")
            `when`(getHistoryListUseCase()).thenReturn(flowOf(testHistory))
            `when`(saveHistoryItemUseCase(anyString())).thenReturn(Unit)

            viewModel =
                InputKeyWordViewModel(
                    getHistoryListUseCase,
                    saveHistoryItemUseCase,
                    clearHistoryUseCase,
                )

            viewModel.saveHistoryItem("test3")

            verify(saveHistoryItemUseCase).invoke("test3")
            assertEquals(testHistory, viewModel.historyListData.value)
        }

    /** 履歴リストのクリアを確認するテスト */
    @Test
    fun testClearHistory() =
        runTest {
            // Given
            `when`(clearHistoryUseCase()).thenReturn(Unit)
            `when`(getHistoryListUseCase.invoke()).thenReturn(flowOf(emptyList()))

            viewModel =
                InputKeyWordViewModel(
                    getHistoryListUseCase,
                    saveHistoryItemUseCase,
                    clearHistoryUseCase,
                )

            viewModel.clearHistory()

            verify(clearHistoryUseCase).invoke()
            assertEquals(emptyList<String>(), viewModel.historyListData.value)
        }
}
