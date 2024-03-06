package com.example.gourmetsearcher.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.example.gourmetsearcher.databinding.LayoutKeyWordHistoryListItemBinding
import com.example.gourmetsearcher.ui.viewholder.KeyWordHistoryViewHolder

/**
 * キーワード履歴のリストのAdapter
 * @param onKeyWordHistoryItemClick キーワード履歴のリストのアイテムをクリックしたときの処理
 */
class KeyWordHistoryAdapter(onKeyWordHistoryItemClick: (String) -> Unit) :
    BaseListAdapter<String, KeyWordHistoryViewHolder>(
        keyWordHistoryDiffCallback,
        onKeyWordHistoryItemClick
    ) {

    /**
     * キーワード履歴のリストのアイテムのViewBindingを生成する
     * @param parent 親View
     * @return キーワード履歴のリストのアイテムのViewBinding
     */
    override fun createViewBinding(parent: ViewGroup): LayoutKeyWordHistoryListItemBinding {
        return LayoutKeyWordHistoryListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    /**
     * キーワード履歴のリストのViewHolderを生成する
     * @param viewBinding キーワード履歴のリストのアイテムのViewBinding
     * @param onItemClicked キーワード履歴のリストのアイテムをクリックしたときの処理
     * @return キーワード履歴のリストのViewHolder
     */
    override fun createViewHolder(
        viewBinding: ViewBinding,
        onItemClicked: (String) -> Unit
    ): KeyWordHistoryViewHolder {
        val binding = viewBinding as LayoutKeyWordHistoryListItemBinding
        return KeyWordHistoryViewHolder(binding, onItemClicked)
    }

    /**
     * キーワード履歴のリストのアイテムをバインドする
     * @param holder キーワード履歴のリストのViewHolder
     * @param item キーワード履歴のリストのアイテム
     */
    override fun bind(holder: KeyWordHistoryViewHolder, item: String) {
        holder.bind(item)
    }

    private companion object {
        /** キーワード履歴のリストのDiffCallback */
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