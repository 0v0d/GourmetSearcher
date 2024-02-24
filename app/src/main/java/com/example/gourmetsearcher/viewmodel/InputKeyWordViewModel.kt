package com.example.gourmetsearcher.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gourmetsearcher.repository.KeyWordHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
//検索画面のViewModel
class InputKeyWordViewModel @Inject constructor(
    private val keyWordHistoryRepository: KeyWordHistoryRepository
) :
    ViewModel() {
    private val _historyList = MutableLiveData<List<String>>()
    val historyList: LiveData<List<String>> get() = _historyList

    init {
        loadHistory()
    }

    fun saveHistoryItem(input: String) {
        keyWordHistoryRepository.saveHistoryItem(input)
        loadHistory()
    }

    fun clearHistory() {
        keyWordHistoryRepository.clearHistory()
        loadHistory()
    }


    private fun loadHistory() {
        _historyList.value = keyWordHistoryRepository.getHistoryList()
    }

    // 入力されたキーワードが空でないかを判定する
    fun isNotInputEmpty(inputText: String): Boolean {
        return inputText.isNotEmpty()
    }
}