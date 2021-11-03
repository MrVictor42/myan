package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class SpinOff(
    @SerializedName("mal_id")
    var malID : Int = 0,
    @SerializedName("name")
    var name : String = ""
)
