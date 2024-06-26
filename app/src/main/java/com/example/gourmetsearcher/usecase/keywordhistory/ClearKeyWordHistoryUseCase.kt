package com.example.gourmetsearcher.usecase.keywordhistory

import com.example.gourmetsearcher.repository.KeyWordHistoryRepository
import javax.inject.Inject

/**
 * 検索履歴をクリアするUseCase
 * @param repository KeyWordHistoryRepository
 */
class ClearKeyWordHistoryUseCase
    @Inject
    constructor(
        private val repository: KeyWordHistoryRepository,
    ) {
        /** 検索履歴をクリアする */
        suspend operator fun invoke() = repository.clearHistory()
    }
