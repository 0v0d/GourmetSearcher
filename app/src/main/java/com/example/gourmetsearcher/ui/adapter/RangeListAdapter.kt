package com.example.gourmetsearcher.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.example.gourmetsearcher.databinding.LayoutRangeListItemBinding
import com.example.gourmetsearcher.ui.viewholder.RangeListViewHolder


class RangeListAdapter(onRangeItemClick: (Int) -> Unit) :
    BaseListAdapter<Int, RangeListViewHolder>(rangeListDiffCallback, onRangeItemClick) {
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
        return RangeListViewHolder(binding, onItemClicked)
    }

    override fun bind(holder: RangeListViewHolder, item: Int) {
        holder.bind(item)
    }

    private companion object {
        // 更新されたデータを判定する
        private val rangeListDiffCallback = object : DiffUtil.ItemCallback<Int>() {
            override fun areItemsTheSame(
                oldValue: Int,
                newValue: Int
            ): Boolean = oldValue == newValue

            override fun areContentsTheSame(
                oldValue: Int,
                newValue: Int
            ): Boolean = oldValue == newValue
        }
    }
}