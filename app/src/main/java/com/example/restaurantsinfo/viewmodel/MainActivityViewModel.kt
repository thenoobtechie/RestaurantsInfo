package com.example.restaurantsinfo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import android.location.Location
import android.os.Handler
import android.widget.Toast
import com.example.restaurantsinfo.utility.BASE_URL
import com.example.restaurantsinfo.api.ApiManager
import com.example.restaurantsinfo.model.CustomResponse
import com.example.restaurantsinfo.model.RestaurantWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivityViewModel(var applicationInstance : Application, var onResponseCallback : Handler.Callback) : AndroidViewModel(applicationInstance) {


    var restaurants: List<RestaurantWrapper> = ArrayList()
    var call: Call<CustomResponse>? = null
    var apiManager: ApiManager? = null
    var location : Location? = null

    init {

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
        apiManager = retrofit.create(ApiManager::class.java)

    }


        fun fetchRestaurants(searchKeyword: String) {

            if (location!=null)
                call = apiManager?.searchRestaurants(searchKeyword, location?.latitude!!, location?.longitude!!)
            else
                call = apiManager?.searchRestaurants(searchKeyword)
            call?.enqueue(object : Callback<CustomResponse> {

                override fun onResponse(call: Call<CustomResponse>, response: Response<CustomResponse>) {

                    response.isSuccessful.let {
                        if (response.body() is CustomResponse) {
                            restaurants = response.body()!!.restaurants
                            onResponseCallback.handleMessage(null)
                        }
                    }
                }

                override fun onFailure(call: Call<CustomResponse>, t: Throwable) {
                    Toast.makeText(getApplication(), "response is not success", Toast.LENGTH_SHORT).show()
                }
            })
        }


}

