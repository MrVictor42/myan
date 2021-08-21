package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("mal_id")
    val mal_id : Int,
    @SerializedName("image_url")
    val image_url : String,
    @SerializedName("name")
    val name : String
)

data class CharacterResponse (
    val characters : MutableList<Character>
)