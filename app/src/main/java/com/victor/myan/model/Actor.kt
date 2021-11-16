package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Actor (
    @SerializedName("mal_id")
    override var malID: Int = 0,
    @SerializedName("title")
    override var title: String = "",
    @SerializedName("synopsis")
    override var synopsis: String = "",
    @SerializedName("status")
    override var status: String = "",
    @SerializedName("image_url")
    override var imageURL: String = "",
    @SerializedName("score")
    override var score: Double = 0.0,
    @SerializedName("title_synonyms")
    override var titleSynonyms: List<String> = arrayListOf(),
    @SerializedName("rank")
    override var rank: Int,
    @SerializedName("type")
    override var type: String = "",
    @SerializedName("related")
    override var related: Related?,
    @SerializedName("genres")
    override var genreList: List<Genre> = arrayListOf(),

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

    override fun toString(): String {
        return "Title : $title MalID : $malID"
    }
}

data class ActorsListCharacterResponse (
    val voice_actors : List<Actor>
)