package com.example.hostel_locator.model

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

class BookingDetails():Parcelable {
    var userUid:String? = null
    var userName:String? = null
    var listingNames:MutableList<String>? = null
    var listingPrices:MutableList<String>? = null
    var listingRatings:MutableList<String>? = null
    var listingLocations:MutableList<String>? = null
    var listingHseTypes:MutableList<String>? = null
    var listingBedsizes:MutableList<String>? = null
    var listingImages:MutableList<String>? = null
    var address : String? = null
    var totalPrice : String? = null
    var phoneNumber : String? = null
    var bookingAccepted : Boolean = false
    var paymentReceived : Boolean = false
    var listingPushKey : String? = null
    var currentTime : Long = 0

    constructor(parcel: Parcel) : this() {
        userUid = parcel.readString()
        userName = parcel.readString()
        address = parcel.readString()
        totalPrice = parcel.readString()
        phoneNumber = parcel.readString()
        bookingAccepted = parcel.readByte() != 0.toByte()
        paymentReceived = parcel.readByte() != 0.toByte()
        listingPushKey = parcel.readString()
        currentTime = parcel.readLong()
    }

    constructor
                (
        userId: String,
        name:String,
        listingPropertyName: ArrayList<String>,
        listingPropertyPrice: ArrayList<String>,
        listingPropertyRating: ArrayList<String>,
        listingPropertyLocation: ArrayList<String>,
        listingPropertyHseType: ArrayList<String>,
        listingPropertyBedsize: ArrayList<String>,
        listingPropertyImage: ArrayList<String>,
        address: String,
        phone: String,
        time: Long,
        listingPushKey: String?,
        b: Boolean,
        b1: Boolean,
    ) : this(){
        this.userUid = userId
        this.userName = name
        this.listingNames = listingPropertyName
        this.listingPrices = listingPropertyPrice
        this.listingRatings = listingPropertyRating
        this.listingLocations = listingPropertyLocation
        this.listingHseTypes = listingPropertyHseType
        this.listingBedsizes = listingPropertyBedsize
        this.listingImages = listingPropertyImage
        this.address = address
        this.phoneNumber = phone
        this.currentTime = time
        this.totalPrice = totalPrice
        this.listingPushKey = listingPushKey
        this.bookingAccepted = bookingAccepted
        this.paymentReceived = paymentReceived
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userUid)
        parcel.writeString(userName)
        parcel.writeString(address)
        parcel.writeString(totalPrice)
        parcel.writeString(phoneNumber)
        parcel.writeByte(if (bookingAccepted) 1 else 0)
        parcel.writeByte(if (paymentReceived) 1 else 0)
        parcel.writeString(listingPushKey)
        parcel.writeLong(currentTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BookingDetails> {
        override fun createFromParcel(parcel: Parcel): BookingDetails {
            return BookingDetails(parcel)
        }

        override fun newArray(size: Int): Array<BookingDetails?> {
            return arrayOfNulls(size)
        }
    }
}