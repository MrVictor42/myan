package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Episode (
    @SerializedName("title")
    var title : String = "",
    @SerializedName("episode")
    var episode : String = "",
    @SerializedName("url")
    var url : String = "",
    @SerializedName("image_url")
    var imageURL : String = ""
)
