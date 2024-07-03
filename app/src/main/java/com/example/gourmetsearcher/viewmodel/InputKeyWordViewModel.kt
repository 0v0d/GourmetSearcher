package com.example.gourmetsearcher.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gourmetsearcher.usecase.keywordhistory.ClearKeyWordHistoryUseCase
import com.example.gourmetsearcher.usecase.keywordhistory.GetKeyWordHistoryUseCase
import com.example.gourmetsearcher.usecase.keywordhistory.SaveKeyWordHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *  キーワード入力画面のViewModel
 *  @param getHistoryListUseCase GetKeyWordHistoryUseCase
 *  @param saveHistoryItemUseCase SaveKeyWordHistoryUseCase
 *  @param clearHistoryUseCase ClearKeyWordHistoryUseCase
 */
@HiltViewModel
class InputKeyWordViewModel
@Inject
constructor(
    private val getHistoryListUseCase: GetKeyWordHistoryUseCase,
    private val saveHistoryItemUseCase: SaveKeyWordHistoryUseCase,
    private val clearHistoryUseCase: ClearKeyWordHistoryUseCase,
) : ViewModel() {
    private val _historyListData = MutableStateFlow<ImmutableList<String>>(persistentListOf())

    /** キーワード履歴リストデータ */
    val historyListData = _historyListData.asStateFlow()

    private val _inputText = mutableStateOf("")

    /** 入力されたテキスト */
    val inputText: State<String> = _inputText

    /** 初期化でキーワード履歴リストを取得する */
    init {
        viewModelScope.launch {
            loadHistory()
        }
    }

    /**
     * 入力されたキーワードを更新する
     * @param newText 新しいテキスト
     */
    fun updateInputText(newText: String) {
        _inputText.value = newText
    }

    /**
     *  入力されたキーワードを保存する
     *  @param input 入力されたキーワード
     */
    fun saveHistoryItem(input: String) {
        viewModelScope.launch {
            saveHistoryItemUseCase(input)
        }
    }

    /** 履歴リストをクリアする */
    fun clearHistory() {
        viewModelScope.launch {
            clearHistoryUseCase()
        }
    }

    /** 履歴リストを取得する */
    private suspend fun loadHistory() {
        try {
            getHistoryListUseCase().collect { historyList ->
                _historyListData.value = historyList
                    .reversed()
                    .toImmutableList()
            }
        } catch (e: Exception) {
            _historyListData.value = persistentListOf()
        }
    }
}
