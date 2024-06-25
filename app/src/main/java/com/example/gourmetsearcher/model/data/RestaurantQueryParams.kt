package com.example.gourmetsearcher.model.data

import kotlinx.serialization.Serializable

/**
 * ホットペッパーグルメAPIのリクエストパラメータ
 * @param key APIキー
 * @param searchTerms 検索条件
 * @param format レスポンスフォーマット
 */
@Serializable
data class RestaurantQueryParams(
    val key: String,
    val searchTerms: SearchTerms,
    val format: String,
)
