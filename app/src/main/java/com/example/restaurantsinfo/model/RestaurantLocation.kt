package com.example.restaurantsinfo.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class RestaurantLocation(var address: String,
                         var locality: String,
                         var city: String,
                         var latitude: Double,
                         var longitude: Double,
                         @field:SerializedName("city_id") var cityId: Long,
                         @field:SerializedName("zip_code") var zipCode : String?,
                         @field:SerializedName("country_id") var countryId : Int,
                         @field:SerializedName("locality_verbose") var localityVerbose : String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(address)
        parcel.writeString(locality)
        parcel.writeString(city)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
        parcel.writeLong(cityId)
        parcel.writeString(zipCode)
        parcel.writeInt(countryId)
        parcel.writeString(localityVerbose)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RestaurantLocation> {
        override fun createFromParcel(parcel: Parcel): RestaurantLocation {
            return RestaurantLocation(parcel)
        }

        override fun newArray(size: Int): Array<RestaurantLocation?> {
            return arrayOfNulls(size)
        }
    }
}