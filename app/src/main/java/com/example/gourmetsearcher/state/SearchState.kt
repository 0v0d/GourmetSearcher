package com.example.gourmetsearcher.state

/** 検索の状態を表すenum */
enum class SearchState {
    DONE,
    NETWORK_ERROR,
    EMPTY_RESULT,
    LOADING
}