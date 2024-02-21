package com.example.gourmetsearcher

import androidx.recyclerview.widget.RecyclerView
import com.example.gourmetsearcher.databinding.LayoutRestaurantListItemBinding

class RestaurantListViewHolder(private val binding: LayoutRestaurantListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: RestaurantData) {
        binding.restaurant = item
    }
}