package com.example.restaurantsinfo

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.transition.TransitionInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import com.example.restaurantsinfo.utility.RESTAURANT_ID
import com.example.restaurantsinfo.utility.loadImage
import com.example.restaurantsinfo.viewmodel.RestaurantDetailActivityViewModel
import com.example.restaurantsinfo.viewmodel.factory.RestaurantDetailViewModelFactory
import kotlinx.android.synthetic.main.activity_restaurant_detail.*
import androidx.core.content.ContextCompat
import com.example.restaurantsinfo.utility.RESTAURANT
import java.lang.Exception


class RestaurantDetailActivity : AppCompatActivity(), Handler.Callback, View.OnClickListener {

    var resId : String = ""
    var viewModel : RestaurantDetailActivityViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_detail)


        setDrawbleColor(zomatoLink, R.color.blue_txt)
        resId = intent.getStringExtra(RESTAURANT_ID)
        viewModel = ViewModelProviders.of(this, RestaurantDetailViewModelFactory(application, resId, this))
            .get(RestaurantDetailActivityViewModel::class.java)
        viewModel?.restaurant = intent.getParcelableExtra(RESTAURANT)
        zomatoLink.setOnClickListener(this)

        //Shared Element transition
        getWindow().sharedElementEnterTransition = TransitionInflater.from(this)
            .inflateTransition(R.transition.shared_element_transation)

        populateViews()
    }


    override fun onClick(v: View?) {

        when(v?.id){
            R.id.zomatoLink -> {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(viewModel?.restaurant?.deeplink)

                try {
                    startActivity(intent)
                }catch (e: Exception){
                    Toast.makeText(applicationContext, "Zomato app not installed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun handleMessage(msg: Message?): Boolean {

        populateViews()
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

    override fun onBackPressed() {
        finishAfterTransition()
    }

    private fun populateViews() {

        viewModel?.let {

            val restaurant = it.restaurant

            name.text = restaurant.name
            ratingBar.rating = restaurant.userRating.aggregateRating.let { it } ?: 0f
            cuisines.text = restaurant.cuisines
            avgCostForTwo.text = restaurant.avgCostForTwo.toString()
            address.text = restaurant.location.address
            loadImage(restaurant.thumb, image, 500)

            if (restaurant.hasOnlineDelivery == 1 && restaurant.isDeliveringNow == 1) {
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
        } ?: Toast.makeText(applicationContext, "Restaurant is null", Toast.LENGTH_SHORT).show()
    }
}