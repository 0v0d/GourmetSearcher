package com.example.gourmetsearcher.manager

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 検索履歴のPreferencesを管理する
 * @param dataStore DataStore<Preferences>
 */
@Singleton
class PreferencesManager
@Inject
constructor(
    private val dataStore: DataStore<Preferences>,
) {
    private val historyKey = stringPreferencesKey(HISTORY_KEY)

    /**
     * 検索履歴を保存する
     * @param input 入力されたキーワード
     */
    suspend fun saveHistoryItem(input: String) {
        dataStore.edit { preferences ->
            val currentList = getHistoryList().first()
            val newList =
                (currentList + input)
                    .distinct()
                    .takeLast(MAX_HISTORY_SIZE)
            preferences[historyKey] = newList.joinToString(",")
        }
    }

    /**
     * 検索履歴を取得する
     * @return 検索履歴
     */
    fun getHistoryList(): Flow<List<String>> =
        dataStore.data.map { preferences ->
            val historyString = preferences[historyKey] ?: ""
            historyString.split(",").filter { it.isNotEmpty() }
        }

    /** 検索履歴をクリアする */
    suspend fun clearHistory() {
        dataStore.edit { preferences ->
            preferences.remove(historyKey)
        }
    }

    private companion object {
        private const val HISTORY_KEY = "historyList"
        private const val MAX_HISTORY_SIZE = 5
    }
}
