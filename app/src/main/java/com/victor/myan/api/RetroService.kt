package com.victor.myan.api

import com.victor.myan.model.RecyclerList
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroService {

    @GET("repositories")
    suspend fun getDataFromApi(@Query("q") query: String) : RecyclerList
}