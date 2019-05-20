package com.example.restaurantsinfo.model

import com.google.gson.annotations.SerializedName

class RestaurantLocation(var address: String,
                         var locality: String,
                         var city: String,
                         var latitude: Double,
                         var longitude: Double,
                         @field:SerializedName("city_id") var cityId: Long,
                         @field:SerializedName("zip_code") var zipCode : String,
                         @field:SerializedName("country_id") var countryId : Int,
                         @field:SerializedName("locality_verbose") var localityVerbose : String)