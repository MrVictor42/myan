package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("mal_id")
    val mal_id : String,
    @SerializedName("image_url")
    val image_url : String,
    @SerializedName("name")
    val name : String,
    @SerializedName("name_kanji")
    val name_kanji : String,
    @SerializedName("nicknames")
    val nicknames : List<String> = arrayListOf(),
    @SerializedName("about")
    val about : String
)

data class AnimeCharacterResponse (
    val characters : MutableList<Character>
)

data class AnimeListCharacterAnimeResponse (
    val animeography : MutableList<Anime>
)

data class MangaListCharacterMangaResponse (
    val mangaography : MutableList<Manga>
)