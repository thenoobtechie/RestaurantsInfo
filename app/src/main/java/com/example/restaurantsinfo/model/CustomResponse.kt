package com.example.restaurantsinfo.model

import com.google.gson.annotations.SerializedName

class CustomResponse(@field:SerializedName("results_found") var resultsFound : Long,
                     @field:SerializedName("results_start") var resutsStart : Int,
                     @field:SerializedName("results_shown") var resultsShown : Int,
                     @field:SerializedName("restaurants") var restaurants : List<RestaurantWrapper>)