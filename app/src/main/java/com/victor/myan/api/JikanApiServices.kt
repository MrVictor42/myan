package com.victor.myan.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface JikanApiServices {

    @GET("schedule/{day}")
    fun getTodayAnime(@Path("day") day : String) : Call<JsonObject>

    @GET("anime/{mal_id}")
    suspend fun getAnime(@Path("mal_id") mal_id : Int) : Call<JsonObject>
}