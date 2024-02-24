package com.example.gourmetsearcher.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.gourmetsearcher.databinding.LayoutRangeListItemBinding

//RangeListを表示するためのViewHolder
class RangeListViewHolder(
    private val binding: LayoutRangeListItemBinding,
    private val onRangeItemClick: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    //rangeTextViewに値をバインド
    fun bind(range: Int) {
        binding.range  = range
        binding.root.setOnClickListener {
            //APIの引数に合わせるために+1する
            val apiRange = layoutPosition + 1
            //RangeListをクリックした時の処理
            onRangeItemClick(apiRange)
        }
    }
}