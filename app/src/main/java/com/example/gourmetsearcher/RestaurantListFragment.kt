package com.example.gourmetsearcher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gourmetsearcher.databinding.FragmentResultListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantListFragment : Fragment() {
    private var _binding: FragmentResultListBinding? = null
    private val binding get() = _binding!!
    private val args: RestaurantListFragmentArgs by navArgs()
    private val adapter by lazy {
        RestaurantListAdapter(restaurantItemClick)
    }
    private val viewModel: RestaurantListViewModel by viewModels()

    private val restaurantItemClick = { it: RestaurantData ->
        navigateToRestaurantDetailFragment(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.searchRestaurants(args.searchTerms)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultListBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeSearchState()
        observeResultList()
        setUpResultListRecyclerView()
    }

    private fun observeSearchState() {
        viewModel.searchState.observe(viewLifecycleOwner) {
            switchSearchState(it)
        }
    }

    private fun switchSearchState(state: SearchState) {
        when (state) {
            SearchState.EMPTY_RESULT -> showError(state, R.string.empty_result_message)
            SearchState.NETWORK_ERROR -> showError(state, R.string.network_error_message)
            SearchState.NONE -> invisibleError()
            SearchState.LOADING -> showLoading()
        }
    }

    private fun showError(state: SearchState, messageResId: Int) {
        binding.resultListRecyclerView.isVisible = false
        binding.loadingProgressBar.isVisible = false
        binding.errorTextView.text = getString(messageResId)
        binding.errorTextView.isVisible = true
        binding.retryButton.isVisible = (state == SearchState.NETWORK_ERROR)
    }

    private fun invisibleError() {
        binding.resultListRecyclerView.isVisible = true
        binding.errorTextView.isVisible = false
        binding.retryButton.isVisible = false
    }

    private fun showLoading() {
        invisibleError()
        binding.loadingProgressBar.isVisible = true
    }

    private fun observeResultList() {
        viewModel.restaurantData.observe(viewLifecycleOwner) { resultList ->
            binding.loadingProgressBar.isVisible = false
            adapter.submitList(resultList)
        }
    }

    private fun setUpResultListRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.resultListRecyclerView.layoutManager = layoutManager
        binding.resultListRecyclerView.adapter = adapter
    }

    private fun navigateToRestaurantDetailFragment(restaurant: RestaurantData) {
        val action = RestaurantListFragmentDirections.actionToRestaurantDetailFragment(
            restaurant.name,
            restaurant
        )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.resultListRecyclerView.adapter = null
        _binding = null
    }
}