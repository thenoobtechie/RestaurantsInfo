package com.example.restaurantsinfo.adapter

import android.support.v4.util.Pair
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.restaurantsinfo.Utility.loadImage
import com.example.restaurantsinfo.model.Restaurant
import com.example.restaurantsinfo.model.RestaurantWrapper
import kotlinx.android.synthetic.main.restaurant_list_item.view.*
import kotlinx.android.synthetic.main.restaurant_list_item.view.address
import kotlinx.android.synthetic.main.restaurant_list_item.view.cuisines
import kotlinx.android.synthetic.main.restaurant_list_item.view.name
import kotlinx.android.synthetic.main.restaurant_list_item.view.ratingBar


class RestaurantsListAdapter(var restaurantsList : List<RestaurantWrapper>, var clickListener: RecyclerViewItemClickListener) : RecyclerView.Adapter<CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(LayoutInflater.from(parent.context).inflate(com.example.restaurantsinfo.R.layout.restaurant_list_item, parent, false), clickListener)
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
    var pair1 : Pair<View, String>? = null
    var pair2 : Pair<View, String>? = null
    var pair3 : Pair<View, String>? = null
    var pair4 : Pair<View, String>? = null
    var pair5 : Pair<View, String>? = null
    var pair6 : Pair<View, String>? = null
    var pair7 : Pair<View, String>? = null
    var pair8 : Pair<View, String>? = null

    init {
        itemView.setOnClickListener {

            clickListener.onListItemClick(it, restaurant, pair1, pair2, pair3, pair4, pair5, pair6, pair7, pair8)
        }
    }

    fun updateViews(wrapper: RestaurantWrapper) {

        pair1 = Pair(itemView.thumb as View, itemView.thumb.transitionName)
        pair2 = Pair(itemView.name as View, itemView.name.transitionName)
        pair3 = Pair(itemView.address as View, itemView.address.transitionName)
        pair4 = Pair(itemView.ratingBar as View, itemView.ratingBar.transitionName)
        pair5 = Pair(itemView.avgCostTitle as View, itemView.avgCostTitle.transitionName)
        pair6 = Pair(itemView.costForTwo as View, itemView.costForTwo.transitionName)
        pair7 = Pair(itemView.locIcon as View, itemView.locIcon.transitionName)
        pair8 = Pair(itemView.cuisines as View, itemView.cuisines.transitionName)

        restaurant = wrapper.restaurant
        itemView.name.text = restaurant?.name
        itemView.address.text = restaurant?.location?.locality
        itemView.cuisines.text = restaurant?.cuisines
        itemView.costForTwo.text = restaurant?.avgCostForTwo.toString()
        itemView.ratingBar.rating = restaurant?.userRating?.aggregateRating!!
        loadImage(restaurant?.thumb!!, itemView.thumb)
    }
}

interface RecyclerViewItemClickListener {
    fun onListItemClick(v : View, restaurant: Restaurant?, vararg pair: Pair<View, String>?)
}