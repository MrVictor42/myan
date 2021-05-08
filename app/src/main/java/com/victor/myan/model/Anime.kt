package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Anime (
    @SerializedName("mal_id")
    val mal_id: Int,
    @SerializedName("title")
    val title : String,
    @SerializedName("synopsis")
    val synopsis : String,
    @SerializedName("image_url")
    val image_url : String,
    @SerializedName("trailer_url")
    val trailer_url : String,
    @SerializedName("status")
    val status : String,
    @SerializedName("score")
    val score : Double
)

data class AnimeList (var anime: List<Anime>?)