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

@AndroidEntryPoint
//検索画面
class InputKeyWordFragment : Fragment() {
    private var _binding: FragmentInputKeyWordBinding? = null
    private val binding get() = _binding!!
    private val viewModel: InputKeyWordViewModel by viewModels()
    private var inputText = ""
    private val rangeListAdapter by lazy {
        RangeListAdapter(rangeItemClick)
    }

    //RangeListをクリックした時の処理を変数に格納
    private val rangeItemClick = { it: Int ->
        navigateToSearchLocationFragment(it)
    }

    private val keyWordHistoryAdapter by lazy {
        KeyWordHistoryAdapter(inputKeyWordHistoryItemClick)
    }

    //履歴をクリックした時の処理
    private val inputKeyWordHistoryItemClick = { it: String ->
        binding.searchInputEditText.setText(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputKeyWordBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.historyList.observe(viewLifecycleOwner) {
            keyWordHistoryAdapter.submitList(it.reversed())
            binding.keyWordClearButton.isVisible = it.isNotEmpty()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchInputClick()
        setUpRangeListRecyclerView()
        setUpKeyWordHistoryRecyclerView()
    }

    private fun setupSearchInputClick() {
        //EditTextに入力があるか監視
        binding.searchInputEditText.doAfterTextChanged { input ->
            val inputString = input.toString()
            //入力があるかどうかを判定
            val isNotEmpty = viewModel.isNotInputEmpty(inputString)
            if (isNotEmpty) {
                inputText = inputString
            }
            binding.keyWordListRecyclerView.isVisible = !isNotEmpty
            binding.keyWordClearButton.isVisible =!isNotEmpty

            binding.searchListRecyclerView.isVisible = isNotEmpty
            binding.selectRangeExplanation.isVisible = isNotEmpty
        }
    }

    private fun setUpRangeListRecyclerView() {
        // RangeListのRecyclerView用のLayoutManager
        val rangeLayoutManager = LinearLayoutManager(requireContext())
        val rangeDividerItemDecoration =
            DividerItemDecoration(requireContext(), rangeLayoutManager.orientation)

        val rangeList = resources.getStringArray(R.array.range_array)
        rangeListAdapter.submitList(rangeList.map { it.toInt() })

        binding.searchListRecyclerView.also {
            it.layoutManager = rangeLayoutManager
            it.addItemDecoration(rangeDividerItemDecoration)
            it.adapter = rangeListAdapter
        }
    }
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

    //ResultListFragmentに遷移
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

    private fun clearKeywordInputText() {
        //EditTextの入力をクリア
        binding.searchInputEditText.text?.clear()
        binding.keyWordClearButton.isVisible = false
        binding.keyWordListRecyclerView.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //メモリリークを防ぐためにadapterを解放
        binding.searchListRecyclerView.adapter = null
        binding.keyWordListRecyclerView.adapter = null
        _binding = null
    }
}