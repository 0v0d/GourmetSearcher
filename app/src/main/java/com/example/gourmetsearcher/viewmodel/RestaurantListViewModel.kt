package com.example.gourmetsearcher.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.gourmetsearcher.model.HotPepperResponse
import com.example.gourmetsearcher.model.RestaurantData
import com.example.gourmetsearcher.model.Results
import com.example.gourmetsearcher.model.SearchTerms
import com.example.gourmetsearcher.repository.HotPepperRepository
import com.example.gourmetsearcher.state.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

/**
 * レストラン検索画面のViewModel
 * @param repository レストラン情報を取得するRepository
 */
@HiltViewModel
class RestaurantListViewModel @Inject constructor(
    private val repository: HotPepperRepository
) : ViewModel() {
    private val _restaurantData = MutableLiveData(HotPepperResponse(Results(emptyList())))
    /** レストラン情報 */
    val restaurantData: LiveData<List<RestaurantData>> = _restaurantData.map { it.results.shops }

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
                performSearch(searchTerms)
            } catch (e: Exception) {
                _searchState.postValue(SearchState.EMPTY_RESULT)
            }
        }
    }

    /**
     * APIから検索結果を取得する
     * @param searchTerms 検索条件
     */
    private suspend fun performSearch(searchTerms: SearchTerms) {
        val response = withContext(Dispatchers.IO) {
            repository.searchHotPepperRepository(searchTerms)
        }
        handleResponse(response)
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
        _searchState.value = SearchState.LOADING
        searchRestaurants(searchTerm)
    }
}