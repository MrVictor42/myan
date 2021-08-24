package com.victor.myan.api

import com.google.gson.JsonObject
import com.victor.myan.model.MangaListTopResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MangaApi {

    @GET("top/manga")
    fun getTopManga() : Call<MangaListTopResponse>

    @GET("manga/{mal_id}")
    suspend fun getManga(@Path("mal_id") mal_id : String) : Response<JsonObject>
}