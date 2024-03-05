package com.example.gourmetsearcher.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.gourmetsearcher.databinding.LayoutKeyWordHistoryListItemBinding

/**
 * キーワード履歴のリストのViewHolder
 * @param binding KeyWordHistoryListItemのViewBinding
 * @param onKeyWordHistoryItemClick キーワード履歴のリストのアイテムをクリックしたときの処理
 */
class KeyWordHistoryViewHolder(
    private val binding: LayoutKeyWordHistoryListItemBinding,
    private val onKeyWordHistoryItemClick: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    /**
     * キーワード履歴のリストのアイテムをバインドする
     * @param item キーワード履歴のリストのアイテム
     */
    fun bind(item: String) {
        binding.keyWordHistory = item
        /** キーワード履歴のリストのアイテムをクリックしたときの処理 */
        binding.root.setOnClickListener {
            onKeyWordHistoryItemClick(item)
        }
    }
}