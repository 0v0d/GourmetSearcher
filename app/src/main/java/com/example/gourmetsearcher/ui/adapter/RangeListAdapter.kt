package com.example.gourmetsearcher.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.gourmetsearcher.databinding.LayoutRangeListItemBinding
import com.example.gourmetsearcher.ui.holder.RangeListViewHolder

/**
 * 範囲のリストのAdapter
 * @param onRangeItemClick 範囲のリストをクリックした時の処理
 */
class RangeListAdapter(
    private val onRangeItemClick: (Int) -> Unit,
) : ListAdapter<Int, RangeListViewHolder>(rangeListDiffCallback) {
    /**
     * ViewHolderのViewBindingを生成する
     * @param parent 親View
     * @param viewType ビュータイプ
     * @return RangeListViewHolder
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RangeListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutRangeListItemBinding.inflate(inflater, parent, false)
        return RangeListViewHolder(binding)
    }

    /**
     * ViewHolderにデータをバインドする
     * @param holder ViewHolder
     * @param position ポジション
     */
    override fun onBindViewHolder(
        holder: RangeListViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position), onRangeItemClick)
    }

    private companion object {
        /** 更新されたデータを判定する */
        private val rangeListDiffCallback =
            object : DiffUtil.ItemCallback<Int>() {
                /**
                 * リストの要素が同じかどうかを判定する
                 * @param oldValue 古い値
                 * @param newValue 新しい値
                 * @return 同じかどうか
                 */
                override fun areItemsTheSame(
                    oldValue: Int,
                    newValue: Int,
                ): Boolean = oldValue == newValue

                override fun areContentsTheSame(
                    oldValue: Int,
                    newValue: Int,
                ): Boolean = oldValue == newValue
            }
    }
}
