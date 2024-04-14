package com.example.gourmetsearcher.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.gourmetsearcher.model.api.HotPepperResponse
import com.example.gourmetsearcher.model.api.Results
import com.example.gourmetsearcher.model.api.Shops
import com.example.gourmetsearcher.model.data.SearchTerms
import com.example.gourmetsearcher.state.SearchState
import com.example.gourmetsearcher.usecase.HotPepperUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

/**
 * レストラン検索画面のViewModel
 * @param hotPepperUseCase ホットペッパーグルメAPIを利用して、レストラン情報を取得するUseCase
 */
@HiltViewModel
class RestaurantListViewModel @Inject constructor(
    private val hotPepperUseCase: HotPepperUseCase
) : ViewModel() {
    private val _restaurantData = MutableLiveData(HotPepperResponse(Results(emptyList())))

    /** レストラン情報 */
    val shops: LiveData<List<Shops>> = _restaurantData.map { it.results.shops }

    private val _searchState = MutableLiveData<SearchState>()

    /** 検索状態 */
    val searchState: LiveData<SearchState> = _searchState
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
                val response = hotPepperUseCase.execute(searchTerms)
                handleResponse(response)
            } catch (e: Exception) {
                _searchState.postValue(SearchState.EMPTY_RESULT)
            }
        }
    }

    /**
     * レスポンスの処理
     * @param response HotPepperResponseAPIからのレスポンス
     */
    private fun handleResponse(response: Response<HotPepperResponse>?) {
        if (response?.body() == null) {
            _searchState.postValue(SearchState.NETWORK_ERROR)
            return
        }

        val isResultEmpty = response.body()?.results?.shops?.isEmpty() ?: true

        if (response.isSuccessful && !isResultEmpty) {
            _searchState.postValue(SearchState.DONE)
            _restaurantData.postValue(response.body())
            return
        }
        _searchState.postValue(SearchState.EMPTY_RESULT)
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