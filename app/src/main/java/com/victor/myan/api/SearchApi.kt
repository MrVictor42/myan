package com.victor.myan.api

import com.victor.myan.model.AnimeListResult
import com.victor.myan.model.MangaListResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("search/anime")
    fun searchAnime(
        @Query("q") searchQuery : String,
        @Query("limit") limit : Int) : Call<AnimeListResult>

    @GET("search/manga")
    fun searchManga(
        @Query("q") searchQuery : String,
        @Query("limit") limit : Int) : Call<MangaListResult>
}