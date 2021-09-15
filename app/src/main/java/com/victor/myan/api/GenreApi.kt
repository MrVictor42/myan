package com.victor.myan.api

import com.victor.myan.model.AnimeListResultResponse
import com.victor.myan.model.MangaListResultResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GenreApi {

    @GET("search/anime")
    fun resultAnimeGenreApi(
        @Query("genre") genre: Int,
        @Query("status") status: String,
        @Query("order_by") orderBy: String,
        @Query("genre_exclude") genreExclude : Int
    ) : Call<AnimeListResultResponse>

    @GET("search/manga")
    fun resultMangaGenreApi(
        @Query("genre") genre: Int,
        @Query("status") status: String,
        @Query("order_by") orderBy: String,
        @Query("genre_exclude") genreExclude : Int
    ) : Call<MangaListResultResponse>
}