package com.example.restaurantsinfo.model

import com.google.gson.annotations.SerializedName

class Restaurant(var id : String,
                 var name : String,
                 var url : String,
                 var deeplink : String,
                 var thumb : String,
                 var cuisines : String,
                 var location: RestaurantLocation,
                 @field:SerializedName("user_rating") var userRating: RestaurantRating,
                 @field:SerializedName("featured_image") var featuredImage : String,
                 @field:SerializedName("average_cost_for_two") var avgCostForTwo : Int,
                 @field:SerializedName("is_delivering_now") var isDeliveringNow : Int,
                 @field:SerializedName("has_online_delivery") var hasOnlineDelivery : Int)