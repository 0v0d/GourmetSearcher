package com.example.gourmetsearcher.model

import java.io.Serializable

data class SearchTerms(
    val keyword: String,
    val location: CurrentLocation,
    val range: Int,
): Serializable
