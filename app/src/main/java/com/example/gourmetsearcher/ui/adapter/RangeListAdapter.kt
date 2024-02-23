package com.example.gourmetsearcher.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.example.gourmetsearcher.databinding.LayoutRangeListItemBinding
import com.example.gourmetsearcher.ui.viewholder.RangeListViewHolder

class RangeListAdapter(onRangeItemClick: (Int) -> Unit) :
    BaseListAdapter<String, RangeListViewHolder, Int>(rangeListDiffCallback, onRangeItemClick) {
    override fun createViewBinding(parent: ViewGroup): LayoutRangeListItemBinding {
        return LayoutRangeListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    override fun createViewHolder(
        viewBinding: ViewBinding,
        onItemClicked: (Int) -> Unit
    ): RangeListViewHolder {
        val binding = viewBinding as LayoutRangeListItemBinding
        return RangeListViewHolder(binding , onItemClicked)
    }

    override fun bind(holder: RangeListViewHolder, item: String) {
        holder.bind(item)
    }

    override fun onViewRecycled(holder: RangeListViewHolder) {
        holder.unbind()
    }

    private companion object {
        // 更新されたデータを判定する
        private val rangeListDiffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(
                oldText: String,
                newText: String
            ): Boolean = oldText == newText

            override fun areContentsTheSame(
                oldText: String,
                newText: String
            ): Boolean = oldText == newText
        }
    }
}