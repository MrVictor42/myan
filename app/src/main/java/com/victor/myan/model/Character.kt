package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Character (
    @SerializedName("mal_id")
    override var malID: Int = 0,
    @SerializedName("image_url")
    override var imageURL: String = "",

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
        0, "", ""
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