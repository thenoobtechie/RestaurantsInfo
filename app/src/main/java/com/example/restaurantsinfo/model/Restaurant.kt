package com.example.restaurantsinfo.model

import android.os.Parcel
import android.os.Parcelable
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
                 @field:SerializedName("has_online_delivery") var hasOnlineDelivery : Int) : Parcelable {


    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readParcelable(RestaurantLocation::class.java.classLoader)!!,
        parcel.readParcelable(RestaurantRating::class.java.classLoader)!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(url)
        parcel.writeString(deeplink)
        parcel.writeString(thumb)
        parcel.writeString(cuisines)
        parcel.writeParcelable(location, flags)
        parcel.writeParcelable(userRating, flags)
        parcel.writeString(featuredImage)
        parcel.writeInt(avgCostForTwo)
        parcel.writeInt(isDeliveringNow)
        parcel.writeInt(hasOnlineDelivery)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Restaurant> {
        override fun createFromParcel(parcel: Parcel): Restaurant {
            return Restaurant(parcel)
        }

        override fun newArray(size: Int): Array<Restaurant?> {
            return arrayOfNulls(size)
        }
    }
}