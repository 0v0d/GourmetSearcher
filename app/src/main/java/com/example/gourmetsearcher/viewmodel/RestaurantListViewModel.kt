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
) : ViewModel() {
    private val _restaurantData = MutableLiveData(HotPepperResponse(Results(emptyList())))
    val restaurantData: LiveData<List<RestaurantData>> = _restaurantData.map { it.results.shops }

    private val _searchState = MutableLiveData<SearchState>()
    val searchState: LiveData<SearchState> = _searchState
    private lateinit var searchTerm: SearchTerms

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

    private suspend fun performSearch(searchTerms: SearchTerms) {
        val response = withContext(Dispatchers.IO) {
            repository.searchHotPepperRepository(searchTerms)
        }
        handleResponse(response)
    }

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

    fun retrySearch() {
        if (searchTerm.keyword.isEmpty()) {
            return
        }
        _searchState.value = SearchState.LOADING
        searchRestaurants(searchTerm)
    }
}