package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Character (
    @SerializedName("mal_id")
    override var malID : Int = 0,
    @SerializedName("image_url")
    override var imageURL: String = "",
    @SerializedName("name")
    var name : String = "",
    @SerializedName("name_kanji")
    var nameKanji : String = "",
    @SerializedName("nicknames")
    var nicknames : List<String> = arrayListOf(),
    @SerializedName("about")
    var about : String = "",

    override var title: String,
    override var status: String,
    override var synopsis: String,
    override var rank: Int,
    override var score: Double,
    override var titleSynonyms: List<String>,
    override var type: String
) : Jikan() {
    constructor() : this(
        0, "", "", "", emptyList(), "", "",
        "", "", 0, 0.0, emptyList(), ""
    )
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