package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Anime (
    @SerializedName("mal_id")
    var malID : Int = 0,
    @SerializedName("title")
    var title : String = "",
    @SerializedName("synopsis")
    var synopsis : String = "",
    @SerializedName("image_url")
    var imageUrl : String = "",
    @SerializedName("trailer_url")
    var trailerUrl : String = "",
    @SerializedName("status")
    var status : String = "",
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
    @SerializedName("favorites")
    var favorites : Int = 0,
    @SerializedName("genres")
    var genreList : List<Genre> = arrayListOf(),
    @SerializedName("licensors")
    var licensorList : List<Licensor> = arrayListOf(),
    @SerializedName("studios")
    var studioList : List<Studio> = arrayListOf(),
    @SerializedName("title_synonyms")
    var titleSynonyms : List<String> = arrayListOf(),
    @SerializedName("opening_themes")
    var openingThemes : List<String> = arrayListOf(),
    @SerializedName("ending_themes")
    var endingThemes : List<String> = arrayListOf(),
    @SerializedName("pictures")
    var pictureList : List<Picture> = arrayListOf()
)

data class AnimeListAiringResponse (
    val results : List<Anime>
)

data class AnimeListTopResponse (
    val top : List<Anime>
)

data class AnimeListRecommendationResponse (
    val recommendations : List<Anime>
)

data class AnimeListResultResponse (
    val results: List<Anime>
)

data class AnimeResponse (
    var animes : List<Anime>? = null
)