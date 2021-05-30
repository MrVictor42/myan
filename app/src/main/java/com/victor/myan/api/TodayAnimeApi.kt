package com.victor.myan.api

import com.google.gson.JsonObject
import com.victor.myan.model.Anime
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TodayAnimeApi {
    @GET("schedule/{day}")
    fun getTodayAnime(@Path("day") currentDay : String) : Call<JsonObject>
}