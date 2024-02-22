package com.example.gourmetsearcher

import androidx.recyclerview.widget.RecyclerView
import com.example.gourmetsearcher.databinding.LayoutRestaurantListItemBinding

class RestaurantListViewHolder(
    private val binding: LayoutRestaurantListItemBinding,
    private val onRestaurantItemClick: (RestaurantData) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: RestaurantData) {
        binding.restaurant = item
        binding.root.setOnClickListener {
            onRestaurantItemClick(item)
        }
    }

    fun unbind() {
        binding.restaurant = null
        binding.icon.setImageDrawable(null)
    }
}