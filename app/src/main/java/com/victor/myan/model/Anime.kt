package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Anime (
    @SerializedName("mal_id")
    var mal_id: Int = 0,
    @SerializedName("title")
    var title : String = "",
    @SerializedName("synopsis")
    var synopsis : String = "",
    @SerializedName("image_url")
    var image_url : String = "",
    @SerializedName("trailer_url")
    var trailer_url : String = "",
    @SerializedName("status")
    var status : String = "",
    @SerializedName("score")
    var score : Double = 0.0
)