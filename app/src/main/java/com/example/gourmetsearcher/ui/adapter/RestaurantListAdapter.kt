package com.example.gourmetsearcher.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.example.gourmetsearcher.databinding.LayoutRestaurantListItemBinding
import com.example.gourmetsearcher.model.RestaurantData
import com.example.gourmetsearcher.ui.viewholder.RestaurantListViewHolder

class RestaurantListAdapter(onRestaurantItemClick: (RestaurantData) -> Unit) :
    BaseListAdapter<RestaurantData, RestaurantListViewHolder, RestaurantData>(
        restaurantDataDiffCallback,
        onRestaurantItemClick
    ) {
    override fun createViewBinding(parent: ViewGroup): ViewBinding {
        return LayoutRestaurantListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    override fun createViewHolder(
        viewBinding: ViewBinding,
        onItemClicked: (RestaurantData) -> Unit
    ): RestaurantListViewHolder {
        val binding = viewBinding as LayoutRestaurantListItemBinding
        return RestaurantListViewHolder(binding, onItemClicked)
    }

    override fun bind(holder: RestaurantListViewHolder, item: RestaurantData) {
        holder.bind(item)
    }

    override fun onViewRecycled(holder: RestaurantListViewHolder) {
        holder.unbind()
    }

    private companion object {
        private val restaurantDataDiffCallback = object : DiffUtil.ItemCallback<RestaurantData>() {
            override fun areItemsTheSame(
                oldRestaurantData: RestaurantData,
                newRestaurantData: RestaurantData
            ): Boolean {
                return oldRestaurantData.id == newRestaurantData.id
            }

            override fun areContentsTheSame(
                oldRestaurantData: RestaurantData,
                newRestaurantData: RestaurantData
            ): Boolean {
                return oldRestaurantData == newRestaurantData
            }
        }
    }
}