package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Actor (
    @SerializedName("mal_id")
    var mal_id : String? = "",
    @SerializedName("name")
    var name : String? = "",
    @SerializedName("image_url")
    var image_url : String? = "",
    @SerializedName("language")
    var language : String? = ""
)

data class ActorsListCharacterResponse (
    val voice_actors : List<Actor>
)