package com.victor.myan.model

import android.os.Parcel
import android.os.Parcelable

open class Jikan() : Parcelable {
    open var malID : Int = 0
    open var imageURL : String = ""

    constructor(parcel: Parcel) : this() {
        malID = parcel.readInt()
        imageURL = parcel.readString()!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(malID)
        parcel.writeString(imageURL)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Jikan> {
        override fun createFromParcel(parcel: Parcel): Jikan {
            return Jikan(parcel)
        }

        override fun newArray(size: Int): Array<Jikan?> {
            return arrayOfNulls(size)
        }
    }
}