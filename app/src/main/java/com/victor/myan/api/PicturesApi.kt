package com.victor.myan.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PicturesApi {

    @GET("anime/{mal_id}/pictures")
    fun getPictures(@Path("mal_id") mal_id : String) : Call<JsonObject>
}