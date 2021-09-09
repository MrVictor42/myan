package com.victor.myan.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PicturesApi {

    @GET("{type}/{mal_id}/pictures")
    fun getPictures(@Path("type") type: String, @Path("mal_id") mal_id : String) : Call<JsonObject>
}