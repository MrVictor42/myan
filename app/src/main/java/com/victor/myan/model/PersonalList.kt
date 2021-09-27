package com.victor.myan.model

data class PersonalList (
    var ID : String = "",
    var userID : String = "",
    var name : String = "",
    var image : String = "",
    var description: String = "",
    var anime : MutableList<Anime> = arrayListOf(),
    var manga : List<Manga> = arrayListOf()
)

data class PersonalListAnime (
    var malID : Int = 0,
    var image : String = "",
    var title : String = "",
    var status : String = ""
)