package com.victor.myan.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object JikanApiInstance {

    private const val BaseURL = "https://api.jikan.moe/v3/"

    fun getJikanApiInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BaseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val retrofit : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BaseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val characterApi : CharacterApi by lazy {
        retrofit.create(CharacterApi::class.java)
    }

    val animeApi : AnimeApi by lazy {
        retrofit.create(AnimeApi::class.java)
    }

    val mangaApi : MangaApi by lazy {
        retrofit.create(MangaApi::class.java)
    }

    val picturesApi : PicturesApi by lazy {
        retrofit.create(PicturesApi::class.java)
    }

    val actorApi : ActorApi by lazy {
        retrofit.create(ActorApi::class.java)
    }

    val searchApi : SearchApi by lazy {
        retrofit.create(SearchApi::class.java)
    }
}