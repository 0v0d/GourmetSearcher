package com.example.gourmetsearcher

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

// キーワード入力画面のViewModel

class InputKeyWordViewModel : ViewModel() {

    // 入力されたキーワードが空でないかを判定する
    fun isInputNotEmpty(inputText: String): Boolean {
        return inputText.isNotEmpty()
    }
}