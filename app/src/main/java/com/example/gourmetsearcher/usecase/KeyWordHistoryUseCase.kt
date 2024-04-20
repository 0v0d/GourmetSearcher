package com.example.gourmetsearcher.usecase

interface KeyWordHistoryUseCase {
    /** 検索履歴を取得する */
    fun getHistoryList(): List<String>

    /** 検索履歴を保存する */
    fun saveHistoryItem(input: String)

    /** 検索履歴を削除する */
    fun clearHistory()
}