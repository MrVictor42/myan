package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Adaptation(
    @SerializedName("mal_id")
    var malID : Int = 0,
    @SerializedName("name")
    var name : String = "",
    @SerializedName("type")
    var type : String = ""
)