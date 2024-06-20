package com.example.gourmetsearcher.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gourmetsearcher.R
import com.example.gourmetsearcher.databinding.FragmentResultListBinding
import com.example.gourmetsearcher.model.domain.ShopsDomain
import com.example.gourmetsearcher.state.SearchState
import com.example.gourmetsearcher.ui.adapter.RestaurantListAdapter
import com.example.gourmetsearcher.viewmodel.RestaurantListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/** レストラン検索結果画面 */
@AndroidEntryPoint
class RestaurantListFragment : Fragment() {
    private var fragmentResultListBinding: FragmentResultListBinding? = null
    private val binding get() = fragmentResultListBinding!!

    private val viewModel: RestaurantListViewModel by viewModels()

    /** ナビゲーションの引数を取得するための変数 */
    private val args: RestaurantListFragmentArgs by navArgs()
    private val adapter by lazy {
        RestaurantListAdapter(restaurantItemClick)
    }

    /** レストランリストをクリックした時の処理 */
    private val restaurantItemClick = { it: ShopsDomain ->
        navigateToRestaurantDetailFragment(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        fragmentResultListBinding = FragmentResultListBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.searchRestaurants(args.searchTerms)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { observeSearchState() }
                launch { observeResultList() }
            }
        }
        setUpResultListRecyclerView()
    }

    /** searchStateの変化を監視する */
    private suspend fun observeSearchState() {
        viewModel.searchState.collect {
            switchSearchState(it)
        }
    }

    /** 検索結果のリストを監視する */
    private suspend fun observeResultList() {
        viewModel.shops.collect { shops ->
            if (shops != null) {
                binding.networkLoadingProgressBar.isVisible = false
                adapter.submitList(shops)
            }
        }
    }

    /**
     * searchStateによって表示を変える
     * @param state 検索状態
     */
    private fun switchSearchState(state: SearchState) {
        when (state) {
            SearchState.EMPTY_RESULT -> showError(state, R.string.empty_result_message)
            SearchState.NETWORK_ERROR -> showError(state, R.string.network_error_message)
            SearchState.SUCCESS -> invisibleError()
            SearchState.LOADING -> showLoading()
        }
    }

    /**
     * エラーを表示する
     * @param state 検索状態
     * @param messageResId エラーメッセージ
     */
    private fun showError(
        state: SearchState,
        messageResId: Int,
    ) = with(binding) {
        resultListRecyclerView.isVisible = false
        networkLoadingProgressBar.isVisible = false
        networkErrorTextView.text = getString(messageResId)
        networkErrorTextView.isVisible = true
        networkRetryButton.isVisible = (state == SearchState.NETWORK_ERROR)
    }

    /** エラーを非表示にする */
    private fun invisibleError() =
        with(binding) {
            resultListRecyclerView.isVisible = true
            networkErrorTextView.isVisible = false
            networkRetryButton.isVisible = false
        }

    /** ローディングを表示する */
    private fun showLoading() {
        invisibleError()
        binding.networkLoadingProgressBar.isVisible = true
    }

    /** 検索結果のリストを表示する */
    private fun setUpResultListRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.resultListRecyclerView.layoutManager = layoutManager
        binding.resultListRecyclerView.adapter = adapter
    }

    /**
     * レストラン詳細画面に遷移する
     * @param restaurant 選択されたレストラン
     */
    private fun navigateToRestaurantDetailFragment(restaurant: ShopsDomain) {
        val action =
            RestaurantListFragmentDirections.actionToRestaurantDetailFragment(
                restaurant.name,
                restaurant,
            )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        binding.resultListRecyclerView.adapter = null
        fragmentResultListBinding = null
        super.onDestroyView()
    }
}
