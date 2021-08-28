package com.victor.myan.api

import com.google.gson.JsonObject
import com.victor.myan.model.Anime
import com.victor.myan.model.AnimeListCarouselResponse
import com.victor.myan.model.AnimeListRecommendationResponse
import com.victor.myan.model.AnimeListTopResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApi {

    @GET("anime/{mal_id}")
    fun getAnime(@Path("mal_id") mal_id : String) : Call<Anime>

    @GET("season/{year}/{season}")
    fun getSeason(@Path("year") currentYear : Int,
                         @Path("season") currentSeason : String) : Call<JsonObject>

    @GET("schedule/{day}")
    fun getTodayAnime(@Path("day") currentDay : String) : Call<JsonObject>

    @GET("top/anime")
    fun getTopAnime() : Call<AnimeListTopResponse>

    @GET("anime/{mal_id}/recommendations")
    fun getRecommendations(@Path("mal_id") mal_id : String) : Call<AnimeListRecommendationResponse>

    @GET("search/anime")
    fun animeListCarousel(
        @Query("status") status : String,
        @Query("order_by") order_by : String,
        @Query("limit") limit : Int
    ) : Call<AnimeListCarouselResponse>
}