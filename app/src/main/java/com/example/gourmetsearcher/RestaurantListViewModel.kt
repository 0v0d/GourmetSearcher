package com.example.gourmetsearcher

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class RestaurantListViewModel(private val repository: HotpepperRepository) : ViewModel() {
    private val _restaurantData = MutableLiveData<HotpepperResponse>()
    val restaurantData: LiveData<List<RestaurantData>> = _restaurantData.map { it.results.shops }

    private val _searchState = MutableLiveData<SearchState>()
    val searchState: LiveData<SearchState> = _searchState
    private lateinit var searchTerm: SearchTerms

    fun searchRestaurants(searchTerms: SearchTerms) {
        viewModelScope.launch {
            try {
                searchTerm = searchTerms
                performSearch(searchTerms)
            } catch (e: Exception) {
                // 例外処理
                _searchState.postValue(SearchState.EMPTY_RESULT)
                _restaurantData.postValue(HotpepperResponse(Results(emptyList())))
            }
        }
    }

    private suspend fun performSearch(searchTerms: SearchTerms) {
        _searchState.postValue(SearchState.LOADING)
        val response = withContext(Dispatchers.IO) {
            repository.getRestaurantData(searchTerms)
        }

        if (response == null) {
            handleError(SearchState.NETWORK_ERROR)
            Log.d("error",response.toString())
            return
        }
        val isNotNullOrEmpty = !response.body()?.results?.shops.isNullOrEmpty()

        if (isNotNullOrEmpty && response.isSuccessful) {
            handleSuccessfulResponse(response)
        } else {
            handleError(SearchState.EMPTY_RESULT)
        }
    }

    private fun handleError(state: SearchState) {
        _searchState.postValue(state)
        _restaurantData.postValue(HotpepperResponse(Results(emptyList())))
    }

    private fun handleSuccessfulResponse(response: Response<HotpepperResponse>) {
        _restaurantData.postValue(response.body())
        _searchState.postValue(SearchState.NONE)
    }

    fun retrySearch() {
        _searchState.postValue(SearchState.LOADING)
        searchRestaurants(searchTerm)
    }
}
