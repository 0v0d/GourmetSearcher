package com.example.gourmetsearcher.repository

import com.example.gourmetsearcher.manager.PreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

/** 検索履歴のリポジトリ */
interface KeyWordHistoryRepository {
    /** 検索履歴を保存する */
    suspend fun saveHistoryItem(input: String)

    /** 検索履歴を取得する */
    fun getHistoryList(): Flow<List<String>>

    /** 検索履歴を削除する */
    suspend fun clearHistory()
}

/**
 *  KeyWordHistoryRepositoryの実装クラス
 * @param preferences PreferencesManger
 */
@Singleton
class KeyWordHistoryRepositoryImpl
    @Inject
    constructor(
        private val preferences: PreferencesManager,
    ) : KeyWordHistoryRepository {
        /**
         * 直近の5件の入力されたキーワードを保存する
         * @param input 入力されたキーワード
         */
        override suspend fun saveHistoryItem(input: String) {
            input.takeIf { it.isNotBlank() }?.let {
                preferences.saveHistoryItem(it)
            }
        }

        /**
         * 検索履歴を取得する
         * @return 検索履歴のFlow
         */
        override fun getHistoryList(): Flow<List<String>> =
            preferences
                .getHistoryList()
                .flowOn(Dispatchers.IO)

        /**
         * 検索履歴をクリアする
         */
        override suspend fun clearHistory() {
            preferences.clearHistory()
        }
    }
