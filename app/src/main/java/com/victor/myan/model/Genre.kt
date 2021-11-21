package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Genre (
    @SerializedName("image")
    override var imageURL: String = "",
    @SerializedName("mal_id")
    override var malID : Int = 0,
    @SerializedName("name")
    var name : String = ""

) : Jikan() {
    constructor() : this (
        "", 0, ""
    )
}