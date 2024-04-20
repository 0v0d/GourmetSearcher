package com.example.gourmetsearcher.viewmodel

import androidx.lifecycle.ViewModel
import com.example.gourmetsearcher.usecase.KeyWordHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 *  キーワード入力画面のViewModel
 *  @param keyWordHistoryUseCase キーワード履歴のUseCase
 */
@HiltViewModel
class InputKeyWordViewModel @Inject constructor(
    private val keyWordHistoryUseCase: KeyWordHistoryUseCase
) : ViewModel() {
    private val _historyListData = MutableStateFlow<List<String>>(emptyList())

    /** キーワード履歴リストデータ */
    val historyListData = _historyListData.asStateFlow()

    /** 初期化でキーワード履歴リストを取得する */
    init {
        loadHistory()
    }

    /**
     *  入力されたキーワードを保存する
     *  @param input 入力されたキーワード
     */
    fun saveHistoryItem(input: String) {
        keyWordHistoryUseCase.saveHistoryItem(input)
        loadHistory()
    }

    /** 履歴リストをクリアする */
    fun clearHistory() {
        keyWordHistoryUseCase.clearHistory()
        loadHistory()
    }

    /** 履歴リストを取得する */
    private fun loadHistory() {
        _historyListData.value = keyWordHistoryUseCase.getHistoryList()
    }
}