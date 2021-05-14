package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("name")
    var name: String = ""
)