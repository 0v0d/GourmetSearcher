package com.example.gourmetsearcher.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gourmetsearcher.R
import com.example.gourmetsearcher.databinding.FragmentInputKeyWordBinding
import com.example.gourmetsearcher.ui.adapter.KeyWordHistoryAdapter
import com.example.gourmetsearcher.ui.adapter.RangeListAdapter
import com.example.gourmetsearcher.viewmodel.InputKeyWordViewModel
import dagger.hilt.android.AndroidEntryPoint

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

    /** 範囲のリストをクリックした時の処理 */
    private val rangeItemClick = { range: Int ->
        navigateToSearchLocationFragment(range)
    }

    private val keyWordHistoryAdapter by lazy {
        KeyWordHistoryAdapter(inputKeyWordHistoryItemClick)
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
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        observeHistoryList()
        return binding.root
    }

    /** キーワード履歴を監視する */
    private fun observeHistoryList() {
        viewModel.historyListData.observe(viewLifecycleOwner) {
            keyWordHistoryAdapter.submitList(it.reversed())
            binding.keyWordClearButton.isVisible = it.isNotEmpty()
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
            val isNotEmpty = viewModel.isNotInputEmpty(inputString)
            if (isNotEmpty) {
                inputText = inputString
            }
            binding.keyWordListRecyclerView.isVisible = !isNotEmpty
            binding.keyWordClearButton.isVisible = !isNotEmpty

            binding.rangeListRecyclerView.isVisible = isNotEmpty
            binding.selectRangeExplanation.isVisible = isNotEmpty
        }
    }

    /** 範囲のリストを設定する */
    private fun setUpRangeListRecyclerView() {
        val rangeLayoutManager = LinearLayoutManager(requireContext())
        val rangeDividerItemDecoration =
            DividerItemDecoration(requireContext(), rangeLayoutManager.orientation)

        val rangeList = resources.getStringArray(R.array.range_array)
        rangeListAdapter.submitList(rangeList.map { it.toInt() })

        binding.rangeListRecyclerView.also {
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
        binding.keyWordListRecyclerView.also {
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
        binding.keyWordClearButton.isVisible = false
        binding.keyWordListRecyclerView.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        /** メモリリークを防ぐためにRecyclerViewのアダプターを解放する */
        binding.rangeListRecyclerView.adapter = null
        binding.keyWordListRecyclerView.adapter = null
        _binding = null
    }
}