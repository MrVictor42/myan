package com.victor.myan.api

import com.victor.myan.model.CharacterResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterApi {
    @GET("anime/{mal_id}/characters_staff")
    fun getCharactersStaff(@Path("mal_id") mal_id : String) : Call<CharacterResponse>
}