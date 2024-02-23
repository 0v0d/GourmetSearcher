package com.example.gourmetsearcher.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.gourmetsearcher.databinding.LayoutRangeListItemBinding

//RangeListを表示するためのViewHolder
class RangeListViewHolder(private val binding: LayoutRangeListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    //rangeTextViewに値をバインド
    fun bind(range: String) {
        binding.range = range
    }
}