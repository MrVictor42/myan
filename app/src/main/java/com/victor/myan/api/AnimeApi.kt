package com.victor.myan.api

import com.google.gson.JsonObject
import com.victor.myan.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApi {

    @GET("anime/{mal_id}")
    fun getAnime(@Path("mal_id") mal_id : Int) : Call<Anime>

    @GET("season/{year}/{season}")
    fun getSeason(@Path("year") currentYear : Int,
                         @Path("season") currentSeason : String) : Call<JsonObject>

    @GET("schedule/{day}")
    fun getTodayAnime(@Path("day") currentDay : String) : Call<JsonObject>

    @GET("top/anime")
    fun getTopAnime() : Call<AnimeListTop>

    @GET("search/anime")
    fun animeListAiring(
        @Query("status") status : String,
        @Query("order_by") order_by : String) : Call<AnimeListResult>
}