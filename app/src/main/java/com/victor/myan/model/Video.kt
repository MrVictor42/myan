package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Video (
    @SerializedName("title")
    var title : String = "",
    @SerializedName("image_url")
    var imageURL : String? = "",
    @SerializedName("episode_id")
    var episode : Int = 0,
    @SerializedName("video_url")
    var videoURL : String? = "",
    @SerializedName("aired")
    var aired : String? = ""
)
