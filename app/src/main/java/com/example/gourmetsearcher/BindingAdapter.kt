package com.example.gourmetsearcher

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

object BindingAdapter {
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