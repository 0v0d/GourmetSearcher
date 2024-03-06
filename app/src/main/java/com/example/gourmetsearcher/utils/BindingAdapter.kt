package com.example.gourmetsearcher.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

/** DataBinding用のAdapter */
object BindingAdapter {
    /**
     * 画像をロードする
     * @param imageView 画像を表示するImageView
     * @param profileImageUrl 画像のURL
     */
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(
        imageView: ImageView,
        profileImageUrl: String
    ) {
        Picasso.get()
            .load(profileImageUrl)
            .into(imageView)
    }
}