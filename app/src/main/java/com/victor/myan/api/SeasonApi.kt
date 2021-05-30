package com.victor.myan.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SeasonApi {
    @GET("season/{year}/{season}")
    fun getCurrentSeason(@Path("year") currentYear : Int,
                         @Path("season") currentSeason : String) : Call<JsonObject>
}