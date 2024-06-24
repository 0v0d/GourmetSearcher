package com.example.gourmetsearcher.usecase.keywordhistory

import com.example.gourmetsearcher.repository.KeyWordHistoryRepository
import javax.inject.Inject

/**
 * 検索履歴を保存するUseCase
 * @param repository KeyWordHistoryRepository
 */
class SaveKeyWordHistoryUseCase
@Inject
constructor(
    private val repository: KeyWordHistoryRepository,
) {
    /**
     * 検索履歴を保存する
     * @param keyword 検索キーワード
     */
    operator fun invoke(keyword: String) = repository.saveHistoryItem(keyword)
}
