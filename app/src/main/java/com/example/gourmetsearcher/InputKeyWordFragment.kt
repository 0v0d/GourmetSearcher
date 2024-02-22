package com.example.gourmetsearcher

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
import com.example.gourmetsearcher.databinding.FragmentInputKeyWordBinding
import dagger.hilt.android.AndroidEntryPoint

//userの入力を受け付けるキーワード入力画面

class InputKeyWordFragment : Fragment() {
    private var _binding: FragmentInputKeyWordBinding? = null
    private val binding get() = _binding!!
    private val viewModel: InputKeyWordViewModel by viewModels()

    //RangeListをクリックした時の処理を変数に格納
    private val rangeItemClickListener =
        object : RangeListAdapter.OnRangeItemClickListener {
            override fun onRangeItemClick(range: Int) {
                navigateToSearchLocationFragment(range)
            }
        }

    private val adapter by lazy {
        RangeListAdapter(rangeItemClickListener)
    }

    private var inputText = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputKeyWordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchInputClick()
        setupRecyclerView()
    }

    private fun setupSearchInputClick() {
        //EditTextに入力があるか監視
        binding.searchInputEditText.doAfterTextChanged { input ->
            val inputString = input.toString()
            //入力があるかどうかを判定
            val isNotEmpty = viewModel.isInputNotEmpty(inputString)
            if (isNotEmpty) {
                inputText = inputString
            }
            binding.resultListRecyclerView.isVisible = isNotEmpty
            binding.selectRangeExplanation.isVisible = isNotEmpty
        }
    }

    private fun setupRecyclerView() {
        //RecyclerViewのレイアウトを設定
        val layoutManager = LinearLayoutManager(requireContext())
        //RecyclerViewの区切り線を設定
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), layoutManager.orientation)

        val rangeList = resources.getStringArray(R.array.range_array).toList()
        adapter.submitList(rangeList)

        binding.resultListRecyclerView.also {
            it.layoutManager = layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = adapter
        }
    }


    //ResultListFragmentに遷移
    private fun navigateToSearchLocationFragment(range: Int) {
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.resultListRecyclerView.adapter = null
        _binding = null
    }
}