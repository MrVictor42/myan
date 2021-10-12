package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("mal_id")
    var malID : Int = 0,
    @SerializedName("image_url")
    var imageUrl : String = "",
    @SerializedName("name")
    var name : String = "",
    @SerializedName("name_kanji")
    var nameKanji : String = "",
    @SerializedName("nicknames")
    var nicknames : List<String> = arrayListOf(),
    @SerializedName("about")
    var about : String = ""
)

data class AnimeCharacterResponse (
    val characters : List<Character>
)

data class AnimeListCharacterAnimeResponse (
    val animeography : List<Anime>
)

data class MangaListCharacterMangaResponse (
    val mangaography : List<Manga>
)