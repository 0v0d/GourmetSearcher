package com.example.gourmetsearcher.usecase.keywordhistory

import com.example.gourmetsearcher.repository.KeyWordHistoryRepository
import javax.inject.Inject

/**
 * 検索履歴を取得するUseCase
 * @param repository KeyWordHistoryRepository
 */
class GetKeyWordHistoryUseCase
    @Inject
    constructor(
        private val repository: KeyWordHistoryRepository,
    ) {
        /**
         * 検索履歴を取得する
         * @return 検索履歴
         */
        operator fun invoke(): List<String> = repository.getHistoryList()
    }
