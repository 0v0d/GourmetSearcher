package com.example.gourmetsearcher.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

/** DataBinding用のAdapter */
object BindingAdapter {
    /**
     * 画像を読み込む
     * @param imageView 画像View
     * @param imageUrl 画像URL
     */
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(
        imageView: ImageView,
        imageUrl: String,
    ) {
        imageView.load(imageUrl) {
            crossfade(true)
        }
    }

    /**
     * 画像を読み込む
     * @param imageView 画像View
     * @param drawable 画像Drawable
     */
    @BindingAdapter("imageResource")
    @JvmStatic
    fun setImageDrawable(
        imageView: ImageView,
        drawable: Drawable?
    ) {
        imageView.setImageDrawable(drawable)
    }
}
