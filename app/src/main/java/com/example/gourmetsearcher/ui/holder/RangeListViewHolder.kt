package com.example.gourmetsearcher.ui.holder

import androidx.recyclerview.widget.RecyclerView
import com.example.gourmetsearcher.databinding.LayoutRangeListItemBinding

/**
 * 範囲のリストのViewHolder
 * @param binding ViewHolderのViewBinding
 */
class RangeListViewHolder(
    private val binding: LayoutRangeListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    /**
     * ViewHolderにデータをバインドする
     * @param range 範囲
     * @param onRangeItemClick 範囲のリストをクリックした時の処理
     */
    fun bind(range: Int, onRangeItemClick: (Int) -> Unit) {
        binding.range = range
        binding.root.setOnClickListener {
            //APIの引数に合わせるために+1する
            val apiRange = layoutPosition + 1
            //RangeListをクリックした時の処理
            onRangeItemClick(apiRange)
        }
    }
}