package com.victor.myan.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PersonalList (
    var userID : String = "",
    var name: String = "",
    var description: String = "",
    var url: String = ""
) : Parcelable