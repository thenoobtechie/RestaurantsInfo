package com.example.restaurantsinfo.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.os.Handler
import android.widget.Toast
import com.example.restaurantsinfo.Utility.BASE_URL
import com.example.restaurantsinfo.api.ApiManager
import com.example.restaurantsinfo.model.Restaurant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestaurantDetailActivityViewModel(application: Application, var resId : String, var onResponseCallback: Handler.Callback) : AndroidViewModel(application) {

    var apiManager : ApiManager? = null
    var restaurant : Restaurant? = null

    init {

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        apiManager = retrofit.create(ApiManager::class.java)

        fetchRestaurant()
    }

    private fun fetchRestaurant() {

        apiManager?.fetchRestaurant(resId)!!.enqueue(object : Callback<Restaurant>{

            override fun onResponse(call: Call<Restaurant>, response: Response<Restaurant>) {
                if (response.isSuccessful){
                    restaurant = response.body()
                    onResponseCallback.handleMessage(null)
                }
            }

            override fun onFailure(call: Call<Restaurant>, t: Throwable) {
                Toast.makeText(getApplication(), "response is not success", Toast.LENGTH_SHORT).show()
            }

        })
    }
}