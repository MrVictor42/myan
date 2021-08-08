package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Studio (
    @SerializedName("type")
    var type : String = "",
    @SerializedName("name")
    var name : String = ""
)
