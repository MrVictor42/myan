package com.victor.myan.interfaces

import com.victor.myan.model.AnimeList
import retrofit2.Response
import retrofit2.http.GET

interface JikanApiServices {

    @GET("season/2021/winter")
    suspend fun getTodayAnime() : Response<AnimeList>
}