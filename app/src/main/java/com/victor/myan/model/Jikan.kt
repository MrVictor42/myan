package com.victor.myan.model

abstract class Jikan {
    abstract var malID : Int
    abstract var title : String
    abstract var status : String
    abstract var synopsis : String
    abstract var imageURL : String
    abstract var rank : Int
    abstract var score : Double
    abstract var titleSynonyms : List<String>
    abstract var type : String
    abstract var related : Related?
    abstract var genreList: List<Genre>
}