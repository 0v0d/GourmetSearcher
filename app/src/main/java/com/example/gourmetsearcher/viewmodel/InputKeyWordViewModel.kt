package com.example.gourmetsearcher.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gourmetsearcher.repository.KeyWordHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 *  キーワード入力画面のViewModel
 *  @param keyWordHistoryRepository キーワード履歴のリポジトリ
 */
@HiltViewModel
class InputKeyWordViewModel @Inject constructor(
    private val keyWordHistoryRepository: KeyWordHistoryRepository
) :
    ViewModel() {
    private val _historyListData = MutableLiveData<List<String>>()

    /** キーワード履歴リストデータ */
    val historyListData: LiveData<List<String>> get() = _historyListData

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

    /**
     * 入力が空でないことを確認する
     * @param inputText 入力されたテキスト
     */
    fun isNotInputEmpty(inputText: String): Boolean {
        return inputText.isNotEmpty()
    }
}