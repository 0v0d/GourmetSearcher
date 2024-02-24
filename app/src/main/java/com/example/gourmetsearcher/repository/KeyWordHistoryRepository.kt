package com.example.gourmetsearcher.repository

import android.content.Context
import android.content.SharedPreferences

class KeyWordHistoryRepository(private val context: Context) {

    private val sharedPrefs: SharedPreferences by lazy {
        context.getSharedPreferences("HistoryPrefs", Context.MODE_PRIVATE)
    }

    fun saveHistoryItem(input: String) {
        val historyList = getHistoryList().toMutableList()

        // 既に存在する項目でないことを確認
        if (!historyList.contains(input)) {
            // 新しい要素を追加し、直近の5件だけを保持するように制限
            historyList.add(input)
            if (historyList.size > 5) {
                val itemToRemove = historyList.removeAt(0)
                sharedPrefs.edit().remove(itemToRemove).apply()  // 削除する項目のキーも削除
            }

            // 各項目を個別のキーで保存
            sharedPrefs.edit().putString(input, input).apply()

            // 順序付けたリストを保存
            sharedPrefs.edit().putString("historyList", historyList.joinToString(",")).apply()
        }
    }

    fun getHistoryList(): List<String> {
        val historyString = sharedPrefs.getString("historyList", "") ?: ""
        return historyString.split(",").filter { it.isNotEmpty() }
    }

    fun clearHistory() {
        // 全てのキーを削除
        getHistoryList().forEach { sharedPrefs.edit().remove(it).apply() }
        sharedPrefs.edit().remove("historyList").apply()
    }
}
