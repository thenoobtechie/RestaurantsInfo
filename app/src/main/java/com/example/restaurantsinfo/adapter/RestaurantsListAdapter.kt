package com.example.restaurantsinfo.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.restaurantsinfo.R
import com.example.restaurantsinfo.Utility.loadImage
import com.example.restaurantsinfo.model.Restaurant
import com.example.restaurantsinfo.model.RestaurantWrapper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.restaurant_list_item.view.*
import java.lang.Exception

class RestaurantsListAdapter(var restaurantsList : List<RestaurantWrapper>, var clickListener: RecyclerViewItemClickListener) : RecyclerView.Adapter<CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.restaurant_list_item, parent, false), clickListener)
    }

    override fun getItemCount(): Int {
        return restaurantsList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.updateViews(restaurantsList[position])
    }
}

class CustomViewHolder(itemView : View, var clickListener: RecyclerViewItemClickListener) : RecyclerView.ViewHolder(itemView) {

    var restaurant : Restaurant? = null

    init {
        itemView.setOnClickListener {
            clickListener.onClick(it, restaurant)
        }
    }

    fun updateViews(wrapper: RestaurantWrapper) {

        restaurant = wrapper.restaurant
        itemView.title.text = restaurant?.name
        itemView.address.text = restaurant?.location?.locality
        itemView.cuisines.text = restaurant?.cuisines
        itemView.costForTwo.text = restaurant?.avgCostForTwo.toString()
        itemView.ratingBar.rating = restaurant?.userRating?.aggregateRating!!
        loadImage(restaurant?.thumb!!, itemView.thumb)
    }
}

interface RecyclerViewItemClickListener {
    fun onClick(v : View, restaurant: Restaurant?)
}