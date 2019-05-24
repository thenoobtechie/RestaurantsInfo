package com.example.restaurantsinfo.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class RestaurantRating(var votes : Int,
                       @field:SerializedName("aggregate_rating") var aggregateRating: Float,
                       @field:SerializedName("rating_text") var ratingText : String,
                       @field:SerializedName("rating_color") var ratingColor : String,
                       @field:SerializedName("custom_rating_text") var customRatingText : String?,
                       @field:SerializedName("custom_rating_text_background") var customRatingTextBackground : String?,
                       @field:SerializedName("rating_tool_tip") var ratingToolTip : String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readFloat(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(votes)
        parcel.writeFloat(aggregateRating)
        parcel.writeString(ratingText)
        parcel.writeString(ratingColor)
        parcel.writeString(customRatingText)
        parcel.writeString(customRatingTextBackground)
        parcel.writeString(ratingToolTip)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RestaurantRating> {
        override fun createFromParcel(parcel: Parcel): RestaurantRating {
            return RestaurantRating(parcel)
        }

        override fun newArray(size: Int): Array<RestaurantRating?> {
            return arrayOfNulls(size)
        }
    }
}