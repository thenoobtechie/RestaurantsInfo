package com.example.restaurantsinfo

import android.arch.lifecycle.ViewModelProviders
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.view.View.GONE
import com.example.restaurantsinfo.Utility.RESTAURANT_ID
import com.example.restaurantsinfo.Utility.loadImage
import com.example.restaurantsinfo.viewmodel.RestaurantDetailActivityViewModel
import com.example.restaurantsinfo.viewmodel.factory.RestaurantDetailViewModelFactory
import kotlinx.android.synthetic.main.activity_restaurant_detail.*
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import android.content.Intent
import android.net.Uri


class RestaurantDetailActivity : AppCompatActivity(), Handler.Callback, View.OnClickListener {
    override fun onClick(v: View?) {

        when(v?.id){
            R.id.zomatoLink -> {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(viewModel?.restaurant?.deeplink)
                startActivity(intent)
            }
        }
    }

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
            setDrawbleColor(deliveryInfo, R.color.green)
        }
        else if (restaurant.hasOnlineDelivery == 1)
            {
                deliveryInfo.text = getString(R.string.not_delivering)
                setDrawbleColor(deliveryInfo, R.color.grey)
            }
        else
            {
                deliveryInfo.text = getString(R.string.delivery_unavailable)
                setDrawbleColor(deliveryInfo, R.color.red)
            }

        emptyLayout.visibility = GONE
        return true
    }

    private fun setDrawbleColor(textView : TextView, color : Int) {

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


        setDrawbleColor(zomatoLink, R.color.blue_txt)
        if (intent.hasExtra(RESTAURANT_ID)) resId = intent.getStringExtra(RESTAURANT_ID)

        viewModel = ViewModelProviders.of(this, RestaurantDetailViewModelFactory(application, resId, this))
            .get(RestaurantDetailActivityViewModel::class.java)

        zomatoLink.setOnClickListener(this)


    }
}