package com.victor.myan.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CategoryApi {

    @GET("search/anime")
    fun categoryByScore(
        @Query("genre") genre : Int,
        @Query("order_by") score : String,
        @Query("type") type : String) : Call<JsonObject>

    @GET("search/anime")
    fun categoryByAiring(
        @Query("genre") genre : Int,
        @Query("status") airing : String,
        @Query("order_by") score : String,
        @Query("type") type : String) : Call<JsonObject>

    @GET("search/anime")
    fun categoryByCompleted(
        @Query("genre") genre : Int,
        @Query("status") status : String,
        @Query("order_by") score : String,
        @Query("type") type : String) : Call<JsonObject>

    @GET("search/anime")
    fun categoryByUpcoming(
        @Query("genre") genre : Int,
        @Query("status") status : String,
        @Query("type") type : String) : Call<JsonObject>

    @GET("search/anime")
    fun slide(
        @Query("status") status : String,
        @Query("order_by") order_by : String,
        @Query("limit") limit : Int) : Call<JsonObject>
}