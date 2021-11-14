package com.victor.myan.api

import com.victor.myan.model.AnimeListRecommendation
import com.victor.myan.model.MangaListRecommendation
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RecommendationApi {
    @GET("anime/{mal_id}/recommendations")
    fun getAnimeRecommendations(@Path("mal_id") mal_id : Int) : Call<AnimeListRecommendation>
    @GET("manga/{mal_id}/recommendations")
    fun getMangaRecommendations(@Path("mal_id") mal_id : Int) : Call<MangaListRecommendation>
}