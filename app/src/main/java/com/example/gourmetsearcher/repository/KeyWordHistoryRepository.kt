package com.example.gourmetsearcher.repository

import android.content.Context
import android.content.SharedPreferences

/**
 *  検索履歴のリポジトリ
 * @param context コンテキスト
 */
class KeyWordHistoryRepository(private val context: Context) {
    /**
     * 検索履歴を保存する
     */
    private val sharedPrefs: SharedPreferences by lazy {
        context.getSharedPreferences("HistoryPrefs", Context.MODE_PRIVATE)
    }

    /**
     * 直近の5件の入力されたキーワードを保存する
     * @param input 入力されたキーワード
     */
    fun saveHistoryItem(input: String) {
        val historyList = getHistoryList().toMutableList()
        /** 既に存在する項目でないことを確認  */
        if (!historyList.contains(input)) {
            historyList.add(input)
            if (historyList.size > 5) {
                val itemToRemove = historyList.removeAt(0)
                /** 既存の項目を削除 */
                sharedPrefs.edit().remove(itemToRemove).apply()
            }
            /** 各項目を個別のキーで保存 */
            sharedPrefs.edit().putString(input, input).apply()
            /** 順序付けたリストを保存 */
            sharedPrefs.edit().putString("historyList", historyList.joinToString(",")).apply()
        }
    }

    /**
     * 検索履歴を取得する
     * @return 検索履歴
     */
    fun getHistoryList(): List<String> {
        val historyString = sharedPrefs.getString("historyList", "") ?: ""
        return historyString.split(",").filter { it.isNotEmpty() }
    }

    /**
     * 検索履歴をクリアする
     */
    fun clearHistory() {
        /** 全てのキーを削除 */
        getHistoryList().forEach { sharedPrefs.edit().remove(it).apply() }
        sharedPrefs.edit().remove("historyList").apply()
    }
}