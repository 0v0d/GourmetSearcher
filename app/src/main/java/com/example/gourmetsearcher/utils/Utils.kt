package com.example.gourmetsearcher.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import com.example.gourmetsearcher.R

/**
 * Webブラウザを開く
 * @param context コンテキスト
 * @param url URL
 */
fun openWebBrowser(
    context: Context,
    url: String
) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    intent.component = ComponentName(
        context.getString(R.string.restaurant_detail_chrome),
        context.getString(R.string.restaurant_detail_chrome_activity)
    )
    context.startActivity(intent)
}

/**
 * Google Mapを開く
 * @param context コンテキスト
 * @param address 住所
 */
fun openMap(
    context: Context,
    address: String
) {
    val mapUrl = context.getString(R.string.restaurant_detail_map_url) + address
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl))
    intent.component = ComponentName(
        context.getString(R.string.restaurant_detail_google_map),
        context.getString(R.string.restaurant_detail_google_map_activity)
    )
    context.startActivity(intent)
}

/**
 *  設定画面を開く
 *  @param context コンテキスト
 */
fun openSettings(context: Context) {
    val intent = Intent(ACTION_LOCATION_SOURCE_SETTINGS)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(intent)
}
