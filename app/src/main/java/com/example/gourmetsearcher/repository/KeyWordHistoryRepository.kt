package com.example.gourmetsearcher.repository

import com.example.gourmetsearcher.manager.PreferencesManager
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

/** 検索履歴のリポジトリ */
interface KeyWordHistoryRepository {
    /** 検索履歴を取得する */
    fun getHistoryList(): List<String>

    /** 検索履歴を保存する */
    fun saveHistoryItem(input: String)

    /** 検索履歴を削除する */
    fun clearHistory()
}

/**
 *  KeyWordHistoryRepositoryの実装クラス
 * @param preferences PreferencesManger
 */
@ViewModelScoped
class KeyWordHistoryRepositoryImpl
@Inject
constructor(
    private val preferences: PreferencesManager,
) : KeyWordHistoryRepository {
    /**
     * 直近の5件の入力されたキーワードを保存する
     * @param input 入力されたキーワード
     */
    override fun saveHistoryItem(input: String) {
        if (input.isEmpty() || input.isBlank()) {
            return
        }
        preferences.saveHistoryItem(input)
    }

    /**
     * 検索履歴を取得する
     * @return 検索履歴
     */
    override fun getHistoryList(): List<String> {
        return preferences.getHistoryList()
    }

    /** 検索履歴をクリアする */
    override fun clearHistory() {
        preferences.clearHistory()
    }
}
