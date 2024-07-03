package com.example.gourmetsearcher.model.data

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.Serializable
import java.net.URLDecoder
import java.net.URLEncoder

/**
 * 検索条件
 * @param keyword キーワード
 * @param location 現在地
 * @param range 範囲
 */
@kotlinx.serialization.Serializable
data class SearchTerms(
    val keyword: String,
    val location: CurrentLocation,
    val range: Int,
) : Serializable {
    companion object {
        @Suppress("ConstPropertyName", "ObjectPropertyNaming")
        private const val serialVersionUID = 1L
    }
}

/**
 * 検索条件をエンコードする
 * @param searchTerms 検索条件
 * @return エンコードされた検索条件
 */
fun encodeSearchTerms(searchTerms: SearchTerms): String {
    val json = Json.encodeToString(searchTerms)
    return URLEncoder.encode(json, "UTF-8")
}

/**
 * 検索条件をデコードする
 * @param encoded エンコードされた検索条件
 * @return デコードされた検索条件
 */
fun decodeSearchTerms(encoded: String): SearchTerms {
    val json = URLDecoder.decode(encoded, "UTF-8")
    return Json.decodeFromString<SearchTerms>(json)
}
