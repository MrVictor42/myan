package com.victor.myan.api

import com.google.gson.JsonObject
import com.victor.myan.enums.CategoriesEnum
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryApi {

    @GET("search/anime")
    fun categoryByScore(
        @Query("genre") genre: Int,
        @Query("order_by") score: CategoriesEnum,
        @Query("type") type: CategoriesEnum
    ) : Call<JsonObject>

    @GET("search/anime")
    fun categoryByAiring(
        @Query("genre") genre: Int,
        @Query("status") airing: CategoriesEnum,
        @Query("order_by") score: CategoriesEnum,
        @Query("type") type: CategoriesEnum
    ) : Call<JsonObject>

    @GET("search/anime")
    fun categoryByCompleted(
        @Query("genre") genre: Int,
        @Query("status") status: CategoriesEnum,
        @Query("order_by") score: CategoriesEnum,
        @Query("type") type: CategoriesEnum
    ) : Call<JsonObject>

    @GET("search/anime")
    fun categoryByUpcoming(
        @Query("genre") genre: Int,
        @Query("status") status: CategoriesEnum,
        @Query("type") type: CategoriesEnum
    ) : Call<JsonObject>

    @GET("search/anime")
    fun slide(
        @Query("status") status : String,
        @Query("order_by") order_by : String,
        @Query("limit") limit : Int
    ) : Call<JsonObject>
}