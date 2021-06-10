package com.victor.myan.api

import com.google.gson.JsonObject
import com.victor.myan.model.Anime
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApi {

    @GET("anime/{mal_id}")
    suspend fun getAnime(@Path("mal_id") mal_id : String) : Response<Anime>

    // All about search

    @GET("search/{choice}")
    fun search(
        @Path("choice") choice : String,
        @Query("q") searchQuery : String,
        @Query("limit") limit : Int) : Call<JsonObject>

    // All about season

    @GET("season/{year}/{season}")
    fun getCurrentSeason(@Path("year") currentYear : Int,
                         @Path("season") currentSeason : String) : Call<JsonObject>

    // All about todayAnime

    @GET("schedule/{day}")
    fun getTodayAnime(@Path("day") currentDay : String) : Call<JsonObject>

    // All about topAnime

    @GET("top/anime")
    fun getTopAnime() : Call<JsonObject>
}