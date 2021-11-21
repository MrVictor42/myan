package com.victor.myan.model

class Categories {
    var title : String = ""
    var type : String = ""
    val categories : MutableList<Anime> = mutableListOf()

    override fun toString(): String {
        return "Title : $title Type: $type Categories : $categories"
    }
}