package com.example.gourmetsearcher.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.gourmetsearcher.databinding.LayoutRangeListItemBinding
import com.example.gourmetsearcher.ui.viewholder.RangeListViewHolder

class RangeListAdapter(private val onRangeItemClick:  (Int) -> Unit) :
    ListAdapter<String, RangeListViewHolder>(rangeListDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RangeListViewHolder {
        val view = LayoutRangeListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RangeListViewHolder(view, onRangeItemClick)
    }

    override fun onBindViewHolder(holder: RangeListViewHolder, position: Int) {
        val item = getItem(position)
        //RangeListViewHolderに値をバインド
        holder.bind(item)
    }

    private companion object {
        //更新されたデータを判定する
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