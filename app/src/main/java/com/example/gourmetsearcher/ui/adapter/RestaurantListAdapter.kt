package com.example.gourmetsearcher.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.example.gourmetsearcher.databinding.LayoutRestaurantListItemBinding
import com.example.gourmetsearcher.model.RestaurantData
import com.example.gourmetsearcher.ui.viewholder.RestaurantListViewHolder

/**
 * レストランリストのAdapter
 * @param onRestaurantItemClick レストランリストをクリックした時の処理
 */
class RestaurantListAdapter(onRestaurantItemClick: (RestaurantData) -> Unit) :
    BaseListAdapter<RestaurantData, RestaurantListViewHolder>(
        restaurantDataDiffCallback,
        onRestaurantItemClick
    ) {
    /**
     * ViewHolderのViewBindingを生成する
     * @param parent 親View
     * @return ViewBinding ViewHolderのViewBinding
     */
    override fun createViewBinding(parent: ViewGroup): ViewBinding {
        return LayoutRestaurantListItemBinding.inflate(
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
        onItemClicked: (RestaurantData) -> Unit
    ): RestaurantListViewHolder {
        val binding = viewBinding as LayoutRestaurantListItemBinding
        return RestaurantListViewHolder(binding, onItemClicked)
    }

    /**
     * ViewHolderにデータをバインドする
     * @param holder ViewHolder
     * @param item リストの要素
     */
    override fun bind(holder: RestaurantListViewHolder, item: RestaurantData) {
        holder.bind(item)
    }

    /**
     * ViewHolderがリサイクルされる際に呼ばれる
     * @param holder ViewHolder
     */
    override fun onViewRecycled(holder: RestaurantListViewHolder) {
        holder.unbind()
    }

    private companion object {
        /** 更新されたデータを判定する */
        private val restaurantDataDiffCallback = object : DiffUtil.ItemCallback<RestaurantData>() {
            /**
             * リストの要素が同じかどうかを判定する
             * @param oldRestaurantData 古いリストの要素
             * @param newRestaurantData 新しいリストの要素
             */
            override fun areItemsTheSame(
                oldRestaurantData: RestaurantData,
                newRestaurantData: RestaurantData
            ): Boolean {
                return oldRestaurantData.id == newRestaurantData.id
            }

            /**
             * リストの要素の内容が同じかどうかを判定する
             * @param oldRestaurantData 古いリストの要素
             * @param newRestaurantData 新しいリストの要素
             */
            override fun areContentsTheSame(
                oldRestaurantData: RestaurantData,
                newRestaurantData: RestaurantData
            ): Boolean {
                return oldRestaurantData == newRestaurantData
            }
        }
    }
}