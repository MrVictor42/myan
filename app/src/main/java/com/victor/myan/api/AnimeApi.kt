package com.victor.myan.api

import com.google.gson.JsonObject
import com.victor.myan.model.Anime
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeApi {

    @GET("anime/{mal_id}")
    suspend fun getAnime(@Path("mal_id") mal_id : String) : Response<Anime>

    @GET("season/{year}/{season}")
    fun getCurrentSeason(@Path("year") currentYear : Int,
                         @Path("season") currentSeason : String) : Call<JsonObject>

    @GET("schedule/{day}")
    fun getTodayAnime(@Path("day") currentDay : String) : Call<JsonObject>

    @GET("top/anime")
    fun getTopAnime() : Call<JsonObject>
}