package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Actor (
    @SerializedName("mal_id")
    var mal_id : String? = "",
    @SerializedName("name")
    var name : String? = "",
    @SerializedName("image_url")
    var image_url : String? = "",
    @SerializedName("given_name")
    var given_name : String? = "",
    @SerializedName("family_name")
    var family_name : String? = "",
    @SerializedName("alternate_names")
    var alternate_names : List<String> = arrayListOf(),
    @SerializedName("birthday")
    var birthday : String? = "",
    @SerializedName("about")
    var about : String = ""
)

data class ActorsListCharacterResponse (
    val voice_actors : List<Actor>
)

data class VoiceActingRolesAnimeResponse (
    val anime : List<Anime>
)

data class VoiceActingRolesCharacterResponse (
    val character : List<Character>
)