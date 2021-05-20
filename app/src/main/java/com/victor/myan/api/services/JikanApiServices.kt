package com.victor.myan.api.services

import com.google.gson.JsonObject
import com.victor.myan.model.Anime
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface JikanApiServices {

    @GET("schedule/{day}")
    fun getTodayAnime(@Path("day") day : String) : Call<JsonObject>

    @GET("anime/{mal_id}")
    suspend fun getAnime(@Path("mal_id") mal_id : String) : Response<Anime>

    @GET("top/anime/1/airing")
    fun getTopAiring() : Call<JsonObject>
}