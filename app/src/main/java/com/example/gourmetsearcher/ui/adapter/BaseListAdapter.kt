package com.example.gourmetsearcher.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseListAdapter<T, VH : RecyclerView.ViewHolder, I>(
    diffCallback: DiffUtil.ItemCallback<T>,
    private val onItemClicked: (I) -> Unit
) : ListAdapter<T, VH>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val viewBinding = createViewBinding(parent)
        return createViewHolder(viewBinding, onItemClicked)
    }

    abstract fun createViewBinding(parent: ViewGroup): ViewBinding
    abstract fun createViewHolder(viewBinding: ViewBinding, onItemClicked: (I) -> Unit): VH
    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        bind(holder, item)
    }
    abstract fun bind(holder: VH, item: T)
}