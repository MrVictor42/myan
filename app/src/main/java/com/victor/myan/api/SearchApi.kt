package com.victor.myan.api

import com.victor.myan.model.AnimeListResultResponse
import com.victor.myan.model.MangaListResultResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("search/anime")
    fun searchAnime(
        @Query("q") searchQuery : String,
        @Query("limit") limit : Int) : Call<AnimeListResultResponse>

    @GET("search/manga")
    fun searchManga(
        @Query("q") searchQuery : String,
        @Query("limit") limit : Int) : Call<MangaListResultResponse>
}