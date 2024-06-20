package com.example.gourmetsearcher.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.gourmetsearcher.databinding.LayoutRestaurantListItemBinding
import com.example.gourmetsearcher.model.domain.ShopsDomain
import com.example.gourmetsearcher.ui.viewholder.RestaurantListViewHolder

/**
 * レストランリストのAdapter
 * @param onRestaurantItemClick レストランリストをクリックした時の処理
 */
class RestaurantListAdapter(
    private val onRestaurantItemClick: (ShopsDomain) -> Unit,
) : ListAdapter<ShopsDomain, RestaurantListViewHolder>(shopsDiffCallback) {
    /**
     * ViewHolderのViewBindingを生成する
     * @param parent 親View
     * @return RestaurantListViewHolder
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RestaurantListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutRestaurantListItemBinding.inflate(inflater, parent, false)
        return RestaurantListViewHolder(binding)
    }

    /**
     * ViewHolderにデータをバインドする
     * @param holder ViewHolder
     * @param position ポジション
     */
    override fun onBindViewHolder(
        holder: RestaurantListViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position), onRestaurantItemClick)
    }

    /**
     * ViewHolderのバインドを解除する
     * @param holder ViewHolder
     */
    override fun onViewRecycled(holder: RestaurantListViewHolder) {
        holder.unbind()
    }

    private companion object {
        /** 更新されたデータを判定する */
        private val shopsDiffCallback =
            object : DiffUtil.ItemCallback<ShopsDomain>() {
                /**
                 * リストの要素が同じかどうかを判定する
                 * @param oldShops 古いリストの要素
                 * @param newShops 新しいリストの要素
                 */
                override fun areItemsTheSame(
                    oldShops: ShopsDomain,
                    newShops: ShopsDomain,
                ): Boolean {
                    return oldShops.id == newShops.id
                }

                override fun areContentsTheSame(
                    oldShops: ShopsDomain,
                    newShops: ShopsDomain,
                ): Boolean {
                    return oldShops == newShops
                }
            }
    }
}
