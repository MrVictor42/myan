package com.victor.myan.api

import com.victor.myan.model.AnimeCharacterResponse
import com.victor.myan.model.Character
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterApi {
    @GET("anime/{mal_id}/characters_staff")
    fun animeCharacters(@Path("mal_id") mal_id : String) : Call<AnimeCharacterResponse>

    @GET("character/{mal_id}")
    fun getCharacter(@Path("mal_id") mal_id: String) : Call<Character>
}