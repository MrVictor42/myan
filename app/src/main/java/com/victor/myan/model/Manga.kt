package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Manga (
    @SerializedName("mal_id")
    var malID : Int = 0,
    @SerializedName("title")
    var title : String = "",
    @SerializedName("volumes")
    var volumes : Int = 0,
    @SerializedName("start_date")
    var startDate : String = "",
    @SerializedName("end_date")
    var endDate : String = "",
    @SerializedName("score")
    var score : Double = 0.0,
    @SerializedName("image_url")
    var imageUrl : String = "",
    @SerializedName("status")
    var status : String = "",
    @SerializedName("chapters")
    var chapters : Int = 0,
    @SerializedName("synopsis")
    var synopsis : String = "",
    @SerializedName("genres")
    var genres : List<Genre> = arrayListOf(),
    @SerializedName("authors")
    var authors : List<Author> = arrayListOf()
)

data class MangaListTopResponse (
    val top : List<Manga>
)

data class MangaListAiringResponse (
    val results : List<Manga>
)

data class MangaListSearchResponse (
    val results: List<Manga>
)