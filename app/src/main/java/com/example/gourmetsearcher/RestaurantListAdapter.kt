package com.example.gourmetsearcher

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.gourmetsearcher.databinding.LayoutRestaurantListItemBinding

class RestaurantListAdapter(private val onRestaurantItemClick: (RestaurantData) -> Unit) :
    ListAdapter<RestaurantData, RestaurantListViewHolder>(restaurantDataDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantListViewHolder {
        val view = LayoutRestaurantListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RestaurantListViewHolder(view, onRestaurantItemClick)
    }

    override fun onBindViewHolder(holder: RestaurantListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onViewRecycled(holder: RestaurantListViewHolder) {
        holder.unbind()
    }

    private companion object {
        private val restaurantDataDiffCallback = object : DiffUtil.ItemCallback<RestaurantData>() {
            override fun areItemsTheSame(
                oldRepositoryData: RestaurantData,
                newRepositoryData: RestaurantData
            ): Boolean {
                return oldRepositoryData.id == newRepositoryData.id
            }

            override fun areContentsTheSame(
                oldRepositoryData: RestaurantData,
                newRepositoryData: RestaurantData
            ): Boolean {
                return oldRepositoryData == newRepositoryData
            }
        }
    }
}