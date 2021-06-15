package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Category (
    @SerializedName("type")
    var type : String = "",
    @SerializedName("image")
    var image : String = "",
    @SerializedName("genre")
    var genre : Int = 0
)