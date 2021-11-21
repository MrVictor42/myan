package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Actor (
    @SerializedName("mal_id")
    override var malID: Int = 0,
    @SerializedName("image_url")
    override var imageURL: String = "",
    var name : String = "",
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
) : Jikan() {

}

data class ActorsListCharacterResponse (
    val voice_actors : List<Actor>
)