package com.victor.myan.model

import android.os.Parcel
import android.os.Parcelable

class Categories() : Parcelable {
    var title : String = ""
    var type : String = ""
    var categories : ArrayList<Jikan> = arrayListOf()

    constructor(parcel: Parcel) : this() {
        title = parcel.readString()!!
        type = parcel.readString()!!
        categories = arrayListOf<Jikan>().apply {
            parcel.readList(this, Jikan::class.java.classLoader)
        }
    }

    override fun toString(): String {
        return "Title : $title Type: $type $categories"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(type)
        parcel.writeList(categories)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Categories> {
        override fun createFromParcel(parcel: Parcel): Categories {
            return Categories(parcel)
        }

        override fun newArray(size: Int): Array<Categories?> {
            return arrayOfNulls(size)
        }
    }
}