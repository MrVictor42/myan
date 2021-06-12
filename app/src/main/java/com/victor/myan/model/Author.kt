package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("name")
    var name: String = ""
)
