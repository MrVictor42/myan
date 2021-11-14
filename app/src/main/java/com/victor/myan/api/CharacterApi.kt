package com.victor.myan.api

import com.victor.myan.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterApi {
    @GET("anime/{mal_id}/characters_staff")
    fun characterList(@Path("mal_id") mal_id : Int) : Call<CharacterListResponse>

    @GET("character/{mal_id}")
    fun getCharacter(@Path("mal_id") malID : Int) : Call<Character>

    @GET("character/{mal_id}")
    fun getCharacterAnime(@Path("mal_id") malID : Int) : Call<AnimeListCharacterResponse>

    @GET("character/{mal_id}")
    fun getCharacterManga(@Path("mal_id") malID : Int) : Call<MangaListCharacterResponse>

    @GET("character/{mal_id}")
    fun getCharacterVoice(@Path("mal_id") malID : Int) : Call<ActorsListCharacterResponse>
}