package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Actor (
    @SerializedName("mal_id")
    var malID : Int = 0,
    @SerializedName("name")
    var name : String = "",
    @SerializedName("image_url")
    var imageUrl : String = "",
    @SerializedName("given_name")
    var givenName : String = "",
    @SerializedName("family_name")
    var familyName : String = "",
    @SerializedName("alternate_names")
    var alternateNames : List<String> = arrayListOf(),
    @SerializedName("birthday")
    var birthday : String = "",
    @SerializedName("about")
    var about : String = ""
)

data class ActorsListCharacterResponse (
    val voice_actors : List<Actor>
)