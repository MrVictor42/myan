package com.victor.myan.api

import com.google.gson.JsonObject
import com.victor.myan.model.Actor
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ActorApi {

    @GET("person/{mal_id}")
    fun getActor(@Path("mal_id") malID : Int) : Call<Actor>

    @GET("person/{mal_id}")
    fun getActorAnime(@Path("mal_id") malID : Int) : Call<JsonObject>
}