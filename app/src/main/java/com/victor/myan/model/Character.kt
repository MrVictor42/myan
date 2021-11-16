package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Character (
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

    @SerializedName("name")
    var name : String = "",
    @SerializedName("name_kanji")
    var nameKanji : String = "",
    @SerializedName("nicknames")
    var nicknames : List<String> = arrayListOf(),
    @SerializedName("about")
    var about : String = ""
) : Jikan() {
    constructor() : this(
        0, "", "", "", "", 0.0, emptyList(), 0, "",
        null, emptyList(), "", "", emptyList(), ""
    )

    override fun toString(): String {
        return "Title : $title MalID : $malID"
    }
}

data class CharacterListResponse (
    val characters : List<Character>
)

data class AnimeListCharacterResponse (
    val animeography : List<Anime>
)

data class MangaListCharacterResponse (
    val mangaography : List<Manga>
)