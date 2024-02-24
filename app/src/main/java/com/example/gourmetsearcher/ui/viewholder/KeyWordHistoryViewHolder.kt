package com.example.gourmetsearcher.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.gourmetsearcher.databinding.LayoutKeyWordHistoryListItemBinding
class KeyWordHistoryViewHolder(
    private val binding: LayoutKeyWordHistoryListItemBinding,
    private val onKeyWordHistoryItemClick: (String) -> Unit):
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: String) {
        binding.keyWordHistory = item
        binding.root.setOnClickListener {
            onKeyWordHistoryItemClick(item)
        }
    }
}