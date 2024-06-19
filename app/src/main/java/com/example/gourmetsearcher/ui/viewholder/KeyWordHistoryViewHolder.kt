package com.example.gourmetsearcher.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.gourmetsearcher.databinding.LayoutKeyWordHistoryListItemBinding

/**
 * キーワード履歴のリストのViewHolder
 * @param binding KeyWordHistoryListItemのViewBinding
 */
class KeyWordHistoryViewHolder(
    private val binding: LayoutKeyWordHistoryListItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    /**
     * ViewHolderにデータをバインドする
     * @param item キーワード履歴のリストのアイテム
     * @param onKeyWordHistoryItemClick キーワード履歴のリストのアイテムをクリックしたときの処理
     */
    fun bind(
        item: String,
        onKeyWordHistoryItemClick: (String) -> Unit,
    ) {
        binding.keyWordHistory = item
        /** キーワード履歴のリストのアイテムをクリックしたときの処理 */
        binding.root.setOnClickListener {
            onKeyWordHistoryItemClick(item)
        }
    }
}
