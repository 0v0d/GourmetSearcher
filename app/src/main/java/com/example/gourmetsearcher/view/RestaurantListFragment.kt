package com.example.gourmetsearcher.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gourmetsearcher.R
import com.example.gourmetsearcher.databinding.FragmentResultListBinding
import com.example.gourmetsearcher.model.RestaurantData
import com.example.gourmetsearcher.state.SearchState
import com.example.gourmetsearcher.ui.adapter.RestaurantListAdapter
import com.example.gourmetsearcher.viewmodel.RestaurantListViewModel
import dagger.hilt.android.AndroidEntryPoint

/** レストラン検索結果画面 */
@Keep
@AndroidEntryPoint
class RestaurantListFragment : Fragment() {
    private val viewModel: RestaurantListViewModel by viewModels()
    private var _binding: FragmentResultListBinding? = null
    private val binding get() = _binding!!

    /** ナビゲーションの引数を取得するための変数 */
    private val args: RestaurantListFragmentArgs by navArgs()
    private val adapter by lazy {
        RestaurantListAdapter(restaurantItemClick)
    }

    /** レストランリストをクリックした時の処理 */
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

    /** searchStateの変化を監視する */
    private fun observeSearchState() {
        viewModel.searchState.observe(viewLifecycleOwner) {
            switchSearchState(it)
        }
    }

    /** 検索結果のリストを監視する */
    private fun observeResultList() {
        viewModel.restaurantData.observe(viewLifecycleOwner) { resultList ->
           if(resultList.isNotEmpty()){
               binding.loadingProgressBar.isVisible = false
               adapter.submitList(resultList)
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
            SearchState.DONE -> invisibleError()
            SearchState.LOADING -> showLoading()
        }
    }

    /**
     * エラーを表示する
     * @param state 検索状態
     * @param messageResId エラーメッセージ
     */
    private fun showError(state: SearchState, messageResId: Int) {
        binding.resultListRecyclerView.isVisible = false
        binding.loadingProgressBar.isVisible = false
        binding.errorTextView.text = getString(messageResId)
        binding.errorTextView.isVisible = true
        binding.retryButton.isVisible = (state == SearchState.NETWORK_ERROR)
    }

    /** エラーを非表示にする */
    private fun invisibleError() {
        binding.resultListRecyclerView.isVisible = true
        binding.errorTextView.isVisible = false
        binding.retryButton.isVisible = false
    }

    /** ローディングを表示する */
    private fun showLoading() {
        invisibleError()
        binding.loadingProgressBar.isVisible = true
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