package com.example.gourmetsearcher.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.gourmetsearcher.databinding.LayoutKeyWordHistoryListItemBinding
import com.example.gourmetsearcher.ui.viewholder.KeyWordHistoryViewHolder

/**
 * キーワード履歴のリストのAdapter
 * @param onKeyWordHistoryItemClick キーワード履歴のリストのアイテムをクリックしたときの処理
 */
class KeyWordHistoryAdapter(
    private val onKeyWordHistoryItemClick: (String) -> Unit,
) : ListAdapter<String, KeyWordHistoryViewHolder>(keyWordHistoryDiffCallback) {
    /**
     * ViewHolderのViewBindingを生成する
     * @param parent 親View
     * @param viewType ビュータイプ
     * @return KeyWordHistoryViewHolder
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): KeyWordHistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutKeyWordHistoryListItemBinding.inflate(inflater, parent, false)
        return KeyWordHistoryViewHolder(binding)
    }

    /**
     * ViewHolderにデータをバインドする
     * @param holder ViewHolder
     * @param position ポジション
     */
    override fun onBindViewHolder(
        holder: KeyWordHistoryViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position), onKeyWordHistoryItemClick)
    }

    companion object {
        /** キーワード履歴のリストのDiffCallback */
        private val keyWordHistoryDiffCallback =
            object : DiffUtil.ItemCallback<String>() {
                /**
                 * リストの要素が同じかどうかを判定する
                 * @param oldValue 古い値
                 * @param newValue 新しい値
                 * @return 同じかどうか
                 */
                override fun areItemsTheSame(
                    oldValue: String,
                    newValue: String,
                ): Boolean = oldValue == newValue

                override fun areContentsTheSame(
                    oldValue: String,
                    newValue: String,
                ): Boolean = oldValue == newValue
            }
    }
}
