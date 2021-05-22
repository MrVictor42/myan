package com.victor.myan.api.services

import com.google.gson.JsonObject
import com.victor.myan.model.Anime
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TodayAnimeServices {
    @GET("schedule/{day}")
    suspend fun getTodayAnime(@Path("day") currentDay : String) : Call<JsonObject>
}