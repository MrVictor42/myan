package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Actor (
    @SerializedName("mal_id")
    override var malID : Int = 0,
    @SerializedName("image_url")
    override var imageURL : String = "",
    @SerializedName("name")
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
    var about : String = "",

    override var title: String,
    override var status: String,
    override var synopsis: String,
    override var rank: Int,
    override var score: Double,
    override var titleSynonyms: List<String>,
    override var type: String
) : Jikan() {
    constructor() : this (
        0, "", "", "", "", emptyList(), "", "",
        "", "", "", 0, 0.0, emptyList(), ""
    )

    override fun toString(): String {
        return "Title : $title MalID : $malID"
    }
}

data class ActorsListCharacterResponse (
    val voice_actors : List<Actor>
)