package com.example.restaurantsinfo.model

import com.google.gson.annotations.SerializedName

class RestaurantRating(var votes : Int,
                       @field:SerializedName("aggregate_rating") var aggregateRating: Float,
                       @field:SerializedName("rating_text") var ratingText : String,
                       @field:SerializedName("rating_color") var ratingColor : String,
                       @field:SerializedName("custom_rating_text") var customRatingText : String,
                       @field:SerializedName("custom_rating_text_background") var customRatingTextBackground : String,
                       @field:SerializedName("rating_tool_tip") var ratingToolTip : String)