package com.victor.myan.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET

interface TopAnimeApi {
    @GET("top/anime")
    fun getTopAnime() : Call<JsonObject>
}