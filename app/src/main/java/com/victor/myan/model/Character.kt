package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("mal_id")
    val malID : Int,
    @SerializedName("image_url")
    val imageUrl : String,
    @SerializedName("name")
    val name : String,
    @SerializedName("name_kanji")
    val nameKanji : String,
    @SerializedName("nicknames")
    val nicknames : List<String> = arrayListOf(),
    @SerializedName("about")
    val about : String
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