package com.victor.myan.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchApi {
    @GET("search/{choice}")
    fun getSearch(
                @Path("choice") choice : String,
                @Query("q") searchQuery : String,
                @Query("page") page : Int,
                @Query("genre") genre : Int,
                @Query("genre_exclude") genre_exclude : Int,
                @Query("limit") limit : Int) : Call<JsonObject>
}