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
class PreferencesManager @Inject constructor(
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
            .takeLast(MAX_HISTORY_SIZE)
        sharedPrefs.edit().putString(HISTORY_KEY, historyList.joinToString(",")).apply()
    }

    /**
     * 検索履歴を取得する
     * @return 検索履歴
     */
    fun getHistoryList(): List<String> {
        val historyString = sharedPrefs.getString(HISTORY_KEY, "") ?: ""
        return historyString.split(",").filter { it.isNotEmpty() }
    }

    /** 検索履歴をクリアする */
    fun clearHistory() {
        sharedPrefs.edit().remove(HISTORY_KEY).apply()
    }

    companion object {
        private const val HISTORY_KEY = "historyList"
        private const val MAX_HISTORY_SIZE = 5
    }
}