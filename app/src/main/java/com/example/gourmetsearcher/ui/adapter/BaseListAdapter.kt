package com.example.gourmetsearcher.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * RecyclerView.Adapterの共通処理をまとめた抽象クラス
 * @param T リストの要素の型
 * @param VH ViewHolderの型
 * @param diffCallback リストの差分を計算するためのコールバック
 * @param onItemClicked リストの要素がクリックされた時の処理
 * @property onItemClicked リストの要素がクリックされた時の処理
 */
abstract class BaseListAdapter<T, VH : RecyclerView.ViewHolder>(
    diffCallback: DiffUtil.ItemCallback<T>,
    private val onItemClicked: (T) -> Unit
) : ListAdapter<T, VH>(diffCallback) {

    /**
     * onCreateViewHolderでViewHolderを生成する際に呼ばれる
     * @param parent 親View
     * @param viewType ビュータイプ
     * @return VH ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val viewBinding = createViewBinding(parent)
        return createViewHolder(viewBinding, onItemClicked)
    }

    /**
     * ViewHolderのViewBindingを生成する
     * @param parent 親View
     * @return ViewBinding ViewHolderのViewBinding
     */
    abstract fun createViewBinding(parent: ViewGroup): ViewBinding

    /**
     * ViewHolderを生成する
     * @param viewBinding ViewHolderのViewBinding
     * @param onItemClicked リストの要素がクリックされた時の処理
     * @return VH ViewHolder
     */
    abstract fun createViewHolder(viewBinding: ViewBinding, onItemClicked: (T) -> Unit): VH

    /**
     * ViewHolderにデータをバインドする際に呼ばれる
     * @param holder ViewHolder
     * @param position リストの位置
     */
    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        bind(holder, item)
    }

    /**
     * ViewHolderにデータをバインドする
     * @param holder ViewHolder
     * @param item リストの要素
     */
    abstract fun bind(holder: VH, item: T)
}