package com.example.gourmetsearcher.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.gourmetsearcher.databinding.LayoutRangeListItemBinding
import com.example.gourmetsearcher.ui.viewholder.RangeListViewHolder

class RangeListAdapter(private val listener: OnRangeItemClickListener) :
    ListAdapter<String, RangeListViewHolder>(rangeListDiffCallback) {
    private companion object {
        //更新されたデータを判定するためのDiffUtil
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

    interface OnRangeItemClickListener {
        //RangeListをクリックした時の処理
        fun onRangeItemClick(range: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RangeListViewHolder {
        val view = LayoutRangeListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RangeListViewHolder(view)
    }

    override fun onBindViewHolder(holder: RangeListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            //positionに1を足してAPIの値と合わせる
            listener.onRangeItemClick(position + 1)
        }
    }
}