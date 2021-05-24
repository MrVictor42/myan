package com.victor.myan.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SeasonAnimeServices {
    @GET("season/{season}")
    fun getCurrentSeason(@Path("season") currentSeason : String) : Call<JsonObject>
}