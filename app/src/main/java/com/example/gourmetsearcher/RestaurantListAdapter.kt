package com.example.gourmetsearcher

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.gourmetsearcher.databinding.LayoutRestaurantListItemBinding

class RestaurantListAdapter(private val listener: OnRestaurantItemClickListener) :
    ListAdapter<RestaurantData, RestaurantListViewHolder>(diff_util) {
    interface OnRestaurantItemClickListener {
        fun onRestaurantItemClick(restaurantData: RestaurantData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantListViewHolder {
        val view = LayoutRestaurantListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RestaurantListViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener.onRestaurantItemClick(item)
        }
    }

    private companion object {
        private val diff_util = object : DiffUtil.ItemCallback<RestaurantData>() {
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