package com.example.gourmetsearcher.state

/** 検索の状態を表すenum */
enum class SearchState {
    SUCCESS,
    NETWORK_ERROR,
    EMPTY_RESULT,
    LOADING,
}
