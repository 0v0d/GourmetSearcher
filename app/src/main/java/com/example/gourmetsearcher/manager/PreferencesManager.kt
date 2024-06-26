package com.example.gourmetsearcher.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 検索履歴のPreferencesを管理する
 * @param context コンテキスト
 */
@Singleton
class PreferencesManager
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) {
        /** 検索履歴を保存する */
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "HistoryPrefs")

        private val historyKey = stringPreferencesKey(HISTORY_KEY)

        /**
         * 検索履歴を保存する
         * @param input 入力されたキーワード
         */
        suspend fun saveHistoryItem(input: String) {
            context.dataStore.edit { preferences ->
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
            context.dataStore.data.map { preferences ->
                val historyString = preferences[historyKey] ?: ""
                historyString.split(",").filter { it.isNotEmpty() }
            }

        /** 検索履歴をクリアする */
        suspend fun clearHistory() {
            context.dataStore.edit { preferences ->
                preferences.remove(historyKey)
            }
        }

        private companion object {
            private const val HISTORY_KEY = "historyList"
            private const val MAX_HISTORY_SIZE = 5
        }
    }
