package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("name")
    var name : String = "",
    @SerializedName("type")
    var type : String = "",
    @SerializedName("mal_id")
    var mal_id : Int = 0,
)