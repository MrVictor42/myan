package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Anime (
    @SerializedName("mal_id")
    override var malID : Int = 0,
    @SerializedName("title")
    var title : String = "",
    @SerializedName("synopsis")
    var synopsis : String = "",
    @SerializedName("status")
    var status : String = "",
    @SerializedName("image_url")
    override var imageURL : String = "",
    @SerializedName("score")
    var score : Double = 0.0,
    @SerializedName("title_synonyms")
    var titleSynonyms : List<String> = arrayListOf(),
    @SerializedName("rank")
    var rank: Int,
    @SerializedName("type")
    var type : String = "",
    @SerializedName("related")
    var related: Related?,
    @SerializedName("genres")
    var genreList: List<Genre> = arrayListOf(),

    @SerializedName("opening_themes")
    var openingList : List<String> = arrayListOf(),
    @SerializedName("ending_themes")
    var endingList : List<String> = arrayListOf(),
    @SerializedName("trailer_url")
    var trailerUrl : String = "",
    @SerializedName("premiered")
    var premiered : String = "",
    @SerializedName("episodes")
    var episodes : Int = 0,
    @SerializedName("duration")
    var duration : String = "",
    @SerializedName("popularity")
    var popularity : Int = 0,
    @SerializedName("favorites")
    var favorites : Int = 0,
    @SerializedName("licensors")
    var licensorList : List<Licensor> = arrayListOf(),
    @SerializedName("studios")
    var studioList : List<Studio> = arrayListOf(),
    @SerializedName("pictures")
    var pictureList : List<Picture> = arrayListOf(),
    var checked: Boolean = false
) : Jikan() {
    constructor() : this (
        0, "", "", "", "", 0.0, emptyList(), 0, "", null, emptyList()
    )

    override fun toString(): String {
        return "Title : $title MalID : $malID"
    }
}

data class AnimeListResult (
    val results : List<Anime>
)

data class AnimeListTop (
    val top : List<Anime>
)

data class AnimeListRecommendation (
    val recommendations : List<Anime>
)

data class AnimeEpisodesList (
    val episodes : List<Video>
)

data class AnimePromoList (
    val promo : List<Video>
)