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

@HiltViewModel
class RestaurantListViewModel @Inject constructor(
    private val repository: HotPepperRepository
) :
    ViewModel() {
    private val _restaurantData = MutableLiveData<HotPepperResponse>()
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
                _restaurantData.postValue(HotPepperResponse(Results(emptyList())))
            }
        }
    }

    private suspend fun performSearch(searchTerms: SearchTerms) {
        _searchState.postValue(SearchState.LOADING)
        val response = withContext(Dispatchers.IO) {
            repository.searchHotPepperRepository(searchTerms)
        }

        if (response == null) {
            handleError(SearchState.NETWORK_ERROR)
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
        _restaurantData.postValue(HotPepperResponse(Results(emptyList())))
    }

    private fun handleSuccessfulResponse(response: Response<HotPepperResponse>) {
        _restaurantData.postValue(response.body())
        _searchState.postValue(SearchState.NONE)
    }

    fun retrySearch() {
        _searchState.postValue(SearchState.LOADING)
        searchRestaurants(searchTerm)
    }
}