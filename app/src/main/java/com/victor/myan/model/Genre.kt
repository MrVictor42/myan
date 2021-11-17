package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Genre (

    override var title: String,
    override var status: String,
    override var synopsis: String,
    override var rank: Int,
    override var score: Double,
    override var titleSynonyms: List<String>,
    override var type: String,
    override var related: Related?,
    override var genreList: List<Genre>,

    @SerializedName("image")
    override var imageURL: String = "",
    @SerializedName("mal_id")
    override var malID : Int = 0,
    @SerializedName("name")
    var name : String = ""

) : Jikan() {
    constructor() : this (
        "", "", "", 0, 0.0, emptyList(), "",
        null, emptyList(), "", 0, ""
    )
}