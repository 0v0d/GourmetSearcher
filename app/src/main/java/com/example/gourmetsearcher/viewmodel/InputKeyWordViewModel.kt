package com.example.gourmetsearcher.viewmodel

import androidx.lifecycle.ViewModel
import com.example.gourmetsearcher.repository.KeyWordHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 *  キーワード入力画面のViewModel
 *  @param keyWordHistoryRepository キーワード履歴のRepository
 */
@HiltViewModel
class InputKeyWordViewModel @Inject constructor(
    private val keyWordHistoryRepository: KeyWordHistoryRepository
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
        keyWordHistoryRepository.saveHistoryItem(input)
        loadHistory()
    }

    /** 履歴リストをクリアする */
    fun clearHistory() {
        keyWordHistoryRepository.clearHistory()
        loadHistory()
    }

    /** 履歴リストを取得する */
    private fun loadHistory() {
        _historyListData.value = keyWordHistoryRepository.getHistoryList()
    }
}