package com.victor.myan.services.interfaces

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface JikanApiServices {

    @GET("schedule/{day}")
    fun getTodayAnime(@Path("day") day : String) : Call<JsonObject>
}