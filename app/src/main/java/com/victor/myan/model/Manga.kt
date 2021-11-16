package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Manga(
    @SerializedName("mal_id")
    override var malID: Int = 0,
    @SerializedName("title")
    override var title: String = "",
    @SerializedName("synopsis")
    override var synopsis: String = "",
    @SerializedName("status")
    override var status: String = "",
    @SerializedName("image_url")
    override var imageURL: String = "",
    @SerializedName("score")
    override var score: Double = 0.0,
    @SerializedName("title_synonyms")
    override var titleSynonyms: List<String> = arrayListOf(),
    @SerializedName("rank")
    override var rank: Int,
    @SerializedName("type")
    override var type: String = "",
    @SerializedName("related")
    override var related: Related?,
    @SerializedName("genres")
    override var genreList: List<Genre> = arrayListOf(),

    @SerializedName("volumes")
    var volumes: Int = 0,
    @SerializedName("start_date")
    var startDate: String = "",
    @SerializedName("end_date")
    var endDate: String = "",
    @SerializedName("chapters")
    var chapters: Int = 0,
    @SerializedName("authors")
    var authors: List<Author> = arrayListOf(),
    @SerializedName("published")
    var published: Publish?
) : Jikan() {
    constructor() : this (
        0, "", "", "", "", 0.0, emptyList(), 0, "",
        null, emptyList(), 0, "", "", 0, emptyList(), null
    )

    override fun toString(): String {
        return "Title : $title MalID : $malID"
    }
}

data class MangaListTopResponse (
    val top : List<Manga>
)

data class MangaListResultResponse (
    val results : List<Manga>
)

data class MangaListRecommendation (
    val recommendations : List<Manga>
)