package com.example.gourmetsearcher.repository

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/** 検索履歴のPreferencesを管理する
 * @param context コンテキスト
 */
@Singleton
class PreferencesManger @Inject
constructor(
    @ApplicationContext private val context: Context
) {
    /** 検索履歴を保存する */
    private val sharedPrefs: SharedPreferences by lazy {
        context.getSharedPreferences("HistoryPrefs", Context.MODE_PRIVATE)
    }

    /**
     * 検索履歴を保存する
     * @param input 入力されたキーワード
     */
    fun saveHistoryItem(input: String) {
        val historyList = (getHistoryList().toMutableList() + input)
            .distinct()
            .takeLast(5)
        sharedPrefs.edit().putString("historyList", historyList.joinToString(",")).apply()
    }

    /**
     * 検索履歴を取得する
     * @return 検索履歴
     */
    fun getHistoryList(): List<String> {
        val historyString = sharedPrefs.getString("historyList", "") ?: ""
        return historyString.split(",").filter { it.isNotEmpty() }
    }

    /** 検索履歴をクリアする */
    fun clearHistory() {
        /** 全てのキーを削除 */
        getHistoryList().forEach { sharedPrefs.edit().remove(it).apply() }
        sharedPrefs.edit().remove("historyList").apply()
    }
}