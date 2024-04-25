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
    private var _binding: FragmentInputKeyWordBinding? = null
    private val binding get() = _binding!!
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
    private val inputKeyWordHistoryItemClick = { it: String ->
        binding.searchInputEditText.setText(it)
        binding.searchInputEditText.setSelection(binding.searchInputEditText.text?.length ?: 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputKeyWordBinding.inflate(inflater, container, false)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
            binding.searchParameters.keyWordListLayout.isVisible = !isNotEmpty
            binding.searchParameters.rangeListLayout.isVisible = isNotEmpty
        }
    }

    /** 範囲のリストを設定する */
    private fun setUpRangeListRecyclerView() {
        val rangeLayoutManager = LinearLayoutManager(requireContext())
        val rangeDividerItemDecoration =
            DividerItemDecoration(requireContext(), rangeLayoutManager.orientation)

        val rangeList = resources.getStringArray(R.array.range_array)
        rangeListAdapter.submitList(rangeList.map { it.toInt() })

        binding.searchParameters.rangeListRecyclerView.also {
            it.layoutManager = rangeLayoutManager
            it.addItemDecoration(rangeDividerItemDecoration)
            it.adapter = rangeListAdapter
        }
    }

    /** キーワード履歴のリストを設定する */
    private fun setUpKeyWordHistoryRecyclerView() {
        val historyLayoutManager = LinearLayoutManager(requireContext())
        val historyDividerItemDecoration =
            DividerItemDecoration(requireContext(), historyLayoutManager.orientation)
        binding.searchParameters.keyWordListRecyclerView.also {
            it.layoutManager = historyLayoutManager
            it.addItemDecoration(historyDividerItemDecoration)
            it.adapter = keyWordHistoryAdapter
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
                range
            )
        findNavController().navigate(action)
        clearKeywordInputText()
    }

    /** キーワード入力をクリアする */
    private fun clearKeywordInputText() {
        binding.searchInputEditText.text?.clear()
        binding.searchParameters.keyWordClearButton.isVisible = false
        binding.searchParameters.keyWordListRecyclerView.isVisible = false
    }

    override fun onDestroyView() {
        /** メモリリークを防ぐためにRecyclerViewのアダプターを解放する */
        binding.searchParameters.rangeListRecyclerView.adapter = null
        binding.searchParameters.keyWordListRecyclerView.adapter = null
        _binding = null
        super.onDestroyView()
    }
}