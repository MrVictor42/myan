package com.victor.myan.api.services

import com.victor.myan.model.Anime
import retrofit2.http.GET
import retrofit2.http.Path

interface TodayAnimeServices {
    @GET("schedule/{day}")
    fun getTodayAnime(@Path("day") day : String) : MutableList<Anime>
}