package com.example.gourmetsearcher.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gourmetsearcher.repository.KeyWordHistoryRepository
import javax.inject.Inject

/**
 *  キーワード入力画面のViewModel
 *  @param keyWordHistoryRepository キーワード履歴のリポジトリ
 */
class InputKeyWordViewModel @Inject constructor(
    private val keyWordHistoryRepository: KeyWordHistoryRepository
) :
    ViewModel() {
    private val _historyList = MutableLiveData<List<String>>()

    /** 履歴リスト */
    val historyList: LiveData<List<String>> get() = _historyList

    /** 初期化で履歴リストを取得する */
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
        _historyList.value = keyWordHistoryRepository.getHistoryList()
    }

    /**
     * 入力が空でないことを確認する
     * @param inputText 入力されたテキスト
     */
    fun isNotInputEmpty(inputText: String): Boolean {
        return inputText.isNotEmpty()
    }
}