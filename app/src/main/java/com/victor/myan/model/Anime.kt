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
    @SerializedName("premiered")
    var premiered : String = "",
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
    var studios : List<Studio> = arrayListOf(),
    @SerializedName("title_synonyms")
    var title_synonyms : List<String> = arrayListOf(),
    @SerializedName("opening_themes")
    var opening_themes : List<String> = arrayListOf(),
    @SerializedName("ending_themes")
    var ending_themes : List<String> = arrayListOf(),
    @SerializedName("pictures")
    var pictures : List<Picture> = arrayListOf()
)

data class AnimeListCarouselResponse (
    val results : List<Anime>
)

data class AnimeListTopResponse (
    val top : List<Anime>
)

data class AnimeListRecommendationResponse (
    val recommendations : List<Anime>
)