package com.example.gourmetsearcher.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gourmetsearcher.R
import com.example.gourmetsearcher.databinding.FragmentInputKeyWordBinding
import com.example.gourmetsearcher.ui.adapter.KeyWordHistoryAdapter
import com.example.gourmetsearcher.ui.adapter.RangeListAdapter
import com.example.gourmetsearcher.viewmodel.InputKeyWordViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/** キーワード入力画面 */
@AndroidEntryPoint
class InputKeyWordFragment : Fragment() {
    private var fragmentInputKeyWordBinding: FragmentInputKeyWordBinding? = null

    private val binding get() = fragmentInputKeyWordBinding!!

    private val viewModel: InputKeyWordViewModel by viewModels()

    private var inputText = ""

    private val rangeListAdapter by lazy {
        RangeListAdapter(rangeItemClick)
    }

    private val keyWordHistoryAdapter by lazy {
        KeyWordHistoryAdapter(inputKeyWordHistoryItemClick)
    }

    /** 範囲のリストをクリックした時の処理 */
    private val rangeItemClick = { range: Int ->
        navigateToSearchLocationFragment(range)
    }

    /** キーワード履歴のリストのアイテムをクリックしたときの処理 */
    private val inputKeyWordHistoryItemClick = { text: String ->
        binding.searchInputEditText.setText(text)
        binding.searchInputEditText.setSelection(
            binding.searchInputEditText.text?.length ?: 0,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        fragmentInputKeyWordBinding =
            FragmentInputKeyWordBinding.inflate(
                inflater,
                container,
                false,
            )

        binding.searchParameters.viewModel = viewModel
        binding.searchParameters.lifecycleOwner = viewLifecycleOwner

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    observeHistoryList()
                }
            }
        }
        return binding.root
    }

    /** キーワード履歴を監視する */
    private suspend fun observeHistoryList() {
        viewModel.historyListData.collect {
            keyWordHistoryAdapter.submitList(it.reversed())
            binding.searchParameters.keyWordClearButton.isVisible = it.isNotEmpty()
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchInputClick()
        setUpRangeListRecyclerView()
        setUpKeyWordHistoryRecyclerView()
    }

    /** searchTermEditTextに入力があるか監視する */
    private fun setupSearchInputClick() {
        /** EditTextに入力があるか監視する */
        binding.searchInputEditText.doAfterTextChanged { input ->
            val inputString = input.toString()

            /** 入力があるかどうか */
            val isNotEmpty = inputString.isNotEmpty()
            if (isNotEmpty) {
                inputText = inputString
            }
            binding.searchParameters.apply {
                keyWordListLayout.isVisible = !isNotEmpty
                rangeListLayout.isVisible = isNotEmpty
            }
        }
    }

    /** 範囲のリストを設定する */
    private fun setUpRangeListRecyclerView() {
        val rangeLayoutManager = LinearLayoutManager(requireContext())
        val rangeDividerItemDecoration =
            DividerItemDecoration(requireContext(), rangeLayoutManager.orientation)

        val rangeList = resources.getStringArray(R.array.input_keyword_range_array)
        rangeListAdapter.submitList(rangeList.map { it.toInt() })

        binding.searchParameters.rangeListRecyclerView.apply {
            layoutManager = rangeLayoutManager
            addItemDecoration(rangeDividerItemDecoration)
            adapter = rangeListAdapter
        }
    }

    /** キーワード履歴のリストを設定する */
    private fun setUpKeyWordHistoryRecyclerView() {
        val historyLayoutManager = LinearLayoutManager(requireContext())

        val historyDividerItemDecoration =
            DividerItemDecoration(
                requireContext(),
                historyLayoutManager.orientation,
            )

        binding.searchParameters.keyWordListRecyclerView.apply {
            layoutManager = historyLayoutManager
            addItemDecoration(historyDividerItemDecoration)
            adapter = keyWordHistoryAdapter
        }
    }

    /** 位置情報検索画面に遷移する
     *  @param range 範囲
     */
    private fun navigateToSearchLocationFragment(range: Int) {
        viewModel.saveHistoryItem(inputText)
        val action =
            InputKeyWordFragmentDirections.actionToSearchLocationFragment(
                inputText,
                range,
            )
        findNavController().navigate(action)
        clearKeywordInputText()
    }

    /** キーワード入力をクリアする */
    private fun clearKeywordInputText() =
        with(binding) {
            searchInputEditText.text?.clear()
            searchParameters.keyWordClearButton.isVisible = false
            searchParameters.keyWordListRecyclerView.isVisible = false
        }

    override fun onDestroyView() {
        /** メモリリークを防ぐためにRecyclerViewのアダプターを解放する */
        binding.searchParameters.apply {
            rangeListRecyclerView.adapter = null
            keyWordListRecyclerView.adapter = null
        }
        fragmentInputKeyWordBinding = null
        super.onDestroyView()
    }
}
