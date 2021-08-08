package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Anime (
    @SerializedName("mal_id")
    var mal_id: String = "",
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
    @SerializedName("airing_start")
    var airing_start : String = "",
    @SerializedName("episodes")
    var episodes : Int = 0,
    @SerializedName("score")
    var score : Double = 0.0,
    @SerializedName("type")
    var type : String = "",
    @SerializedName("duration")
    var duration : String = "",
    @SerializedName("popularity")
    var popularity : Int = 0,
    @SerializedName("members")
    var members : Int = 0,
    @SerializedName("favorites")
    var favorites : Int = 0,
    @SerializedName("genres")
    var genres : List<Genre> = arrayListOf(),
    @SerializedName("licensors")
    var licensors : List<Licensor> = arrayListOf(),
    @SerializedName("studios")
    var studios : List<Studio> = arrayListOf()
)