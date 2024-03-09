package com.example.gourmetsearcher.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.gourmetsearcher.databinding.LayoutRestaurantListItemBinding
import com.example.gourmetsearcher.model.RestaurantData

/**
 * レストランリストのViewHolder
 * @param binding ViewHolderのViewBinding
 * @param onRestaurantItemClick レストランリストをクリックした時の処理
 */
class RestaurantListViewHolder(
    private val binding: LayoutRestaurantListItemBinding,
    private val onRestaurantItemClick: (RestaurantData) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    /**
     * ViewHolderにデータをバインドする
     * @param item レストラン情報
     */
    fun bind(item: RestaurantData) {
        binding.restaurant = item
        // レストランリストをクリックした時の処理
        binding.root.setOnClickListener {
            onRestaurantItemClick(item)
        }
    }

    /** ViewHolderのバインドを解除する */
    fun unbind() {
        binding.restaurant = null
        binding.restaurantListItemLayout.icon.setImageDrawable(null)
    }
}