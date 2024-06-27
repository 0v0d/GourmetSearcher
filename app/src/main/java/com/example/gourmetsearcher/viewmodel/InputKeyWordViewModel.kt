package com.example.gourmetsearcher.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gourmetsearcher.usecase.keywordhistory.ClearKeyWordHistoryUseCase
import com.example.gourmetsearcher.usecase.keywordhistory.GetKeyWordHistoryUseCase
import com.example.gourmetsearcher.usecase.keywordhistory.SaveKeyWordHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val _historyListData = MutableStateFlow<List<String>>(emptyList())

    /** キーワード履歴リストデータ */
    val historyListData = _historyListData.asStateFlow()

    /** 初期化でキーワード履歴リストを取得する */
    init {
        viewModelScope.launch {
            loadHistory()
        }
    }

    /** 履歴リストを取得する */
    private suspend fun loadHistory() {
        try {
            getHistoryListUseCase().collect { historyList ->
                _historyListData.value = historyList
            }
        } catch (e: Exception) {
            _historyListData.value = emptyList()
        }
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
}
