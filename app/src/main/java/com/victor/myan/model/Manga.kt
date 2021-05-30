package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Manga (
    @SerializedName("mal_id")
    var mal_id : String = "",
    @SerializedName("title")
    var title : String = "",
    @SerializedName("volumes")
    var volumes : Int = 0,
    @SerializedName("start_date")
    var start_date : String = "",
    @SerializedName("end_date")
    var end_date : String = "",
    @SerializedName("score")
    var score : Double = 0.0,
    @SerializedName("image_url")
    var image_url : String = ""
)