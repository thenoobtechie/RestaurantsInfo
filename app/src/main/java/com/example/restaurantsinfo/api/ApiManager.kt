package com.example.restaurantsinfo.api

import com.example.restaurantsinfo.model.CustomResponse
import com.example.restaurantsinfo.model.Restaurant
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiManager {

    @Headers("user-key: ee6d3ff764b58bd0286a431a3ae0e9b2")
    @GET("search")
    fun searchRestaurants(@Query("q") searchStr : String) : Call<CustomResponse>

    @Headers("user-key: ee6d3ff764b58bd0286a431a3ae0e9b2")
    @GET("search")
    fun searchRestaurants(@Query("q") searchStr : String, @Query("lat") lat : Double, @Query("lon") lon : Double) : Call<CustomResponse>

    @Headers("user-key: ee6d3ff764b58bd0286a431a3ae0e9b2")
    @GET("restaurant")
    fun fetchRestaurant(@Query("res_id") resId : String) : Call<Restaurant>
}