package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("mal_id")
    var mal_id : Int = 0,
    @SerializedName("image_url")
    var image_url : String = "",
    @SerializedName("name")
    var name : String = ""
)