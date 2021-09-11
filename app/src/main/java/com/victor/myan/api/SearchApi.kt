package com.victor.myan.api

import com.google.gson.JsonObject
import com.victor.myan.model.AnimeListSearchResponse
import com.victor.myan.model.MangaListSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchApi {

    @GET("search/anime")
    fun searchAnime(
        @Query("q") searchQuery : String,
        @Query("limit") limit : Int) : Call<AnimeListSearchResponse>

    @GET("search/manga")
    fun searchManga(
        @Query("q") searchQuery : String,
        @Query("limit") limit : Int) : Call<MangaListSearchResponse>
}