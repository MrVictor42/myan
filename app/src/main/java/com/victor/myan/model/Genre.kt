package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Genre (
    @SerializedName("name")
    var name : String = "",
    @SerializedName("image")
    var image : String = "",
    @SerializedName("mal_id")
    var malID : Int = 0,
)