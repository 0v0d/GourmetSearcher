package com.example.gourmetsearcher.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.example.gourmetsearcher.databinding.LayoutRangeListItemBinding
import com.example.gourmetsearcher.ui.viewholder.RangeListViewHolder

/**
 * 範囲のリストのAdapter
 * @param onRangeItemClick 範囲のリストをクリックした時の処理
 */
class RangeListAdapter(onRangeItemClick: (Int) -> Unit) :
    BaseListAdapter<Int, RangeListViewHolder>(
        rangeListDiffCallback,
        onRangeItemClick
    ) {
    /**
     * ViewHolderのViewBindingを生成する
     * @param parent 親View
     * @return ViewBinding ViewHolderのViewBinding
     */
    override fun createViewBinding(parent: ViewGroup): LayoutRangeListItemBinding {
        return LayoutRangeListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    /**
     * ViewHolderを生成する
     * @param viewBinding ViewHolderのViewBinding
     * @param onItemClicked リストの要素がクリックされた時の処理
     * @return VH ViewHolder
     */
    override fun createViewHolder(
        viewBinding: ViewBinding,
        onItemClicked: (Int) -> Unit
    ): RangeListViewHolder {
        val binding = viewBinding as LayoutRangeListItemBinding
        return RangeListViewHolder(binding, onItemClicked)
    }

    /**
     * ViewHolderにデータをバインドする
     * @param holder ViewHolder
     * @param item リストの要素
     */
    override fun bind(holder: RangeListViewHolder, item: Int) {
        holder.bind(item)
    }

    private companion object {
        /** 更新されたデータを判定する */
        private val rangeListDiffCallback = object : DiffUtil.ItemCallback<Int>() {
            /**
             * リストの要素が同じかどうかを判定する
             * @param oldValue 古い値
             * @param newValue 新しい値
             * @return 同じかどうか
             */
            override fun areItemsTheSame(
                oldValue: Int,
                newValue: Int
            ): Boolean = oldValue == newValue

            /**
             * リストの要素が同じかどうかを判定する
             * @param oldValue 古い値
             * @param newValue 新しい値
             * @return 同じかどうか
             */
            override fun areContentsTheSame(
                oldValue: Int,
                newValue: Int
            ): Boolean = oldValue == newValue
        }
    }
}