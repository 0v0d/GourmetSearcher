package com.example.gourmetsearcher.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.example.gourmetsearcher.databinding.LayoutKeyWordHistoryListItemBinding
import com.example.gourmetsearcher.ui.viewholder.KeyWordHistoryViewHolder

class KeyWordHistoryAdapter(onKeyWordHistoryItemClick: (String) -> Unit) :
    BaseListAdapter<String, KeyWordHistoryViewHolder>(
        keyWordHistoryDiffCallback,
        onKeyWordHistoryItemClick
    ) {
    override fun createViewBinding(parent: ViewGroup): LayoutKeyWordHistoryListItemBinding {
        return LayoutKeyWordHistoryListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    override fun createViewHolder(
        viewBinding: ViewBinding,
        onItemClicked: (String) -> Unit
    ): KeyWordHistoryViewHolder {
        val binding = viewBinding as LayoutKeyWordHistoryListItemBinding
        return KeyWordHistoryViewHolder(binding, onItemClicked)
    }

    override fun bind(holder: KeyWordHistoryViewHolder, item: String) {
        holder.bind(item)
    }

    private companion object {
        // 更新されたデータを判定する
        private val keyWordHistoryDiffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(
                oldValue: String,
                newValue: String
            ): Boolean = oldValue == newValue

            override fun areContentsTheSame(
                oldValue: String,
                newValue: String
            ): Boolean = oldValue == newValue
        }
    }
}