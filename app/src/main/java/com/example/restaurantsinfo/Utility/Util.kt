package com.example.restaurantsinfo.Utility

import android.widget.ImageView
import com.example.restaurantsinfo.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.restaurant_list_item.view.*


const val RESTAURANT_ID = "restaurantId"
const val BASE_URL = "https://developers.zomato.com/api/v2.1/"

fun loadImage(url : String, iv : ImageView) {

    var urlMutable : String? = url
    if (url == "") urlMutable = null
    Picasso.get().load(urlMutable)
        .placeholder(R.drawable.no_image_icon)
        .resize(200, 200)
        .into(iv)
}

fun loadImage(url : String, iv : ImageView, size : Int) {

    var urlMutable : String? = url
    if (url == "") urlMutable = null

    Picasso.get().load(urlMutable)
        .placeholder(R.drawable.no_image_icon)
        .resize(size, size)
        .into(iv)
}
