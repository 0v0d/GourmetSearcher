package com.example.gourmetsearcher.model

import java.io.Serializable

data class SearchTerms(
    val keyword: String,
    val location: LastLocation,
    val range: Int,
): Serializable
