package com.example.gourmetsearcher.ui.holder

import androidx.recyclerview.widget.RecyclerView
import com.example.gourmetsearcher.databinding.LayoutRestaurantListItemBinding
import com.example.gourmetsearcher.model.domain.ShopsDomain

/**
 * レストランリストのViewHolder
 * @param binding ViewHolderのViewBinding
 */
class RestaurantListViewHolder(
    private val binding: LayoutRestaurantListItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    /**
     * ViewHolderにデータをバインドする
     * @param item レストラン情報
     * @param onRestaurantItemClick レストランリストをクリックした時の処理
     */
    fun bind(
        item: ShopsDomain,
        onRestaurantItemClick: (ShopsDomain) -> Unit,
    ) {
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
