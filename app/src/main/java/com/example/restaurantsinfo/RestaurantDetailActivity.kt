package com.example.restaurantsinfo

import android.arch.lifecycle.ViewModelProviders
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.View.GONE
import com.example.restaurantsinfo.Utility.RESTAURANT_ID
import com.example.restaurantsinfo.Utility.loadImage
import com.example.restaurantsinfo.viewmodel.RestaurantDetailActivityViewModel
import com.example.restaurantsinfo.viewmodel.factory.RestaurantDetailViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_restaurant_detail.*
import kotlinx.android.synthetic.main.activity_restaurant_detail.view.*
import kotlinx.android.synthetic.main.restaurant_list_item.view.*
import android.R.color
import android.support.v4.content.ContextCompat
import android.graphics.drawable.Drawable
import android.widget.TextView


class RestaurantDetailActivity : AppCompatActivity(), Handler.Callback {

    var resId : String = ""
    var viewModel : RestaurantDetailActivityViewModel? = null

    override fun handleMessage(msg: Message?): Boolean {

        val restaurant = viewModel?.restaurant
        name.text = restaurant?.name
        ratingBar.rating = restaurant?.userRating?.aggregateRating!!
        cuisines.text = restaurant.cuisines
        avgCostForTwo.text = restaurant.avgCostForTwo.toString()
        address.text = restaurant.location.address
        loadImage(restaurant.thumb, image, 500)

        if (restaurant.hasOnlineDelivery == 1 && restaurant.isDeliveringNow == 1){
            deliveryInfo.text = getString(R.string.delivery_available)
            setTextDrawbleColor(deliveryInfo, R.color.green)
        }
        else if (restaurant.hasOnlineDelivery == 1)
            {
                deliveryInfo.text = getString(R.string.not_delivering)
                setTextDrawbleColor(deliveryInfo, R.color.grey)
            }
        else
            {
                deliveryInfo.text = getString(R.string.delivery_unavailable)
                setTextDrawbleColor(deliveryInfo, R.color.red)
            }

        emptyLayout.visibility = GONE
        return true
    }

    private fun setTextDrawbleColor(textView : TextView, color : Int) {

        for (drawable in textView.getCompoundDrawables()) {
            drawable?.let {
                drawable.colorFilter = PorterDuffColorFilter(
                    ContextCompat.getColor(applicationContext, color),
                    PorterDuff.Mode.SRC_IN
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_detail)

        if (intent.hasExtra(RESTAURANT_ID)) resId = intent.getStringExtra(RESTAURANT_ID)

        viewModel = ViewModelProviders.of(this, RestaurantDetailViewModelFactory(application, resId, this))
            .get(RestaurantDetailActivityViewModel::class.java)


    }
}