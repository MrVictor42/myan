package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Picture (
    @SerializedName("large")
    var large : String = "",
    @SerializedName("image_url")
    var imageURL : String = ""
)