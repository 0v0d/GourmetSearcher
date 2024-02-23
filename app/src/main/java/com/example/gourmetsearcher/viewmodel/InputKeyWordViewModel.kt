package com.example.gourmetsearcher.viewmodel

import androidx.lifecycle.ViewModel

// キーワード入力画面のViewModel

class InputKeyWordViewModel : ViewModel() {

    // 入力されたキーワードが空でないかを判定する
    fun isInputNotEmpty(inputText: String): Boolean {
        return inputText.isNotEmpty()
    }
}