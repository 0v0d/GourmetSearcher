package com.example.gourmetsearcher.model.data

import java.io.Serializable

/**
 * 検索条件
 * @param keyword キーワード
 * @param location 現在地
 * @param range 範囲
 */
data class SearchTerms(
    val keyword: String,
    val location: CurrentLocation,
    val range: Int,
) : Serializable
