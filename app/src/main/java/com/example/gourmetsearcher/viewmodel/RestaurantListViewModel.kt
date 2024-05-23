package com.example.gourmetsearcher.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gourmetsearcher.model.api.HotPepperResponse
import com.example.gourmetsearcher.model.data.SearchTerms
import com.example.gourmetsearcher.model.domain.ShopsDomain
import com.example.gourmetsearcher.model.domain.toDomain
import com.example.gourmetsearcher.repository.HotPepperRepository
import com.example.gourmetsearcher.state.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

/**
 * レストラン検索画面のViewModel
 * @param hotPepperRepository ホットペッパーグルメAPIを利用して、レストラン情報を取得するRepository
 */
@HiltViewModel
class RestaurantListViewModel
    @Inject
    constructor(
        private val hotPepperRepository: HotPepperRepository,
    ) : ViewModel() {
        private val _shops = MutableStateFlow<List<ShopsDomain>?>(null)

        /** レストラン情報 */
        val shops = _shops.asStateFlow()

        private val _searchState = MutableStateFlow(SearchState.LOADING)

        /** 検索状態 */
        val searchState = _searchState.asStateFlow()

        private lateinit var searchTerm: SearchTerms

        /**
         * レストラン検索
         * @param searchTerms 検索条件
         */
        fun searchRestaurants(searchTerms: SearchTerms) {
            _searchState.value = SearchState.LOADING
            viewModelScope.launch {
                try {
                    searchTerm = searchTerms
                    val response = hotPepperRepository.execute(searchTerms)
                    handleResponse(response)
                } catch (e: Exception) {
                    _searchState.value = SearchState.EMPTY_RESULT
                }
            }
        }

        /**
         * レスポンスの処理
         * @param response HotPepperResponseAPIからのレスポンス
         */
        private fun handleResponse(response: Response<HotPepperResponse>?) {
            if (response?.body() == null) {
                _searchState.value = SearchState.NETWORK_ERROR
                return
            }
            val repositories = response.body()?.results?.shops?.map { it.toDomain() }
            val isResultEmpty = repositories.isNullOrEmpty()

            if (response.isSuccessful && !isResultEmpty) {
                _searchState.value = SearchState.SUCCESS
                _shops.value = repositories
            } else {
                _searchState.value = SearchState.EMPTY_RESULT
            }
        }

        /**
         * 検索結果が空でないかを返す
         * @return true: 検索結果が空でない, false: 検索結果が空
         */
        fun retrySearch() {
            if (searchTerm.keyword.isEmpty()) {
                return
            }
            searchRestaurants(searchTerm)
        }
    }
