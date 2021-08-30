package com.victor.myan.api

import com.victor.myan.model.Actor
import com.victor.myan.model.Anime
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ActorApi {

    @GET("person/{mal_id}")
    fun getActor(@Path("mal_id") mal_id : String) : Call<Actor>
}