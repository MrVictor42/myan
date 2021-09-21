package com.victor.myan.model

data class PersonalList (
    var ID : String = "",
    var userID : String = "",
    var name: String = "",
    var description: String = "",
    var type : String = "",
    var anime : List<Anime> = arrayListOf(),
    var manga : List<Manga> = arrayListOf()
)