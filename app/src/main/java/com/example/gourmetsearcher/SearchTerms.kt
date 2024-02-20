package com.example.gourmetsearcher

import java.io.Serializable

data class SearchTerms(
    val keyword: String,
    val location: LastLocation,
    val range: Int,
): Serializable
