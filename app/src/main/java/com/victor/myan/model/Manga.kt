package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Manga(
    @SerializedName("mal_id")
    override var malID: Int = 0,
    @SerializedName("title")
    var title: String = "",
    @SerializedName("synopsis")
    var synopsis: String = "",
    @SerializedName("status")
    var status: String = "",
    @SerializedName("image_url")
    override var imageURL: String = "",
    @SerializedName("score")
    var score: Double = 0.0,
    @SerializedName("title_synonyms")
    var titleSynonyms: List<String> = arrayListOf(),
    @SerializedName("rank")
    var rank: Int,
    @SerializedName("type")
    var type: String = "",
    @SerializedName("related")
    var related: Related?,
    @SerializedName("genres")
    var genreList: List<Genre> = emptyList(),
    var titleCategory : String = "",

    @SerializedName("volumes")
    var volumes: Int = 0,
    @SerializedName("start_date")
    var startDate: String = "",
    @SerializedName("end_date")
    var endDate: String = "",
    @SerializedName("chapters")
    var chapters: Int = 0,
    @SerializedName("authors")
    var authors: List<Author> = emptyList(),
    @SerializedName("published")
    var published: Publish?,
    var checked: Boolean = false
) : Jikan() {
    constructor() : this (
        0, "", "", "", "", 0.0, emptyList(), 0,
        "", null, emptyList(), "", 0, "", "",
        0, emptyList(), null, false
    )

    override fun toString(): String {
        return "Title : $title MalID : $malID"
    }
}

data class MangaListTop (
    val top : List<Manga>
)

data class MangaListResult (
    val results : List<Manga>
)

data class MangaListRecommendation (
    val recommendations : List<Manga>
)