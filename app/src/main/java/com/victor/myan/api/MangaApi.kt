package com.victor.myan.api

import com.victor.myan.model.Manga
import com.victor.myan.model.MangaListResultResponse
import com.victor.myan.model.MangaListTopResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MangaApi {

    @GET("top/manga")
    fun getTopManga() : Call<MangaListTopResponse>

    @GET("manga/{mal_id}")
    fun getManga(@Path("mal_id") malID : Int) : Call<Manga>

    @GET("search/manga")
    fun mangaListAiring(
        @Query("status") status : String,
        @Query("order_by") order_by : String) : Call<MangaListResultResponse>
}