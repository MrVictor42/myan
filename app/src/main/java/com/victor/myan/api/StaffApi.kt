package com.victor.myan.api

import com.victor.myan.model.Staff
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StaffApi {
    @GET("anime/{mal_id}/characters_staff")
    suspend fun getCharactersStaff(@Path("mal_id") mal_id : Int) : Response<Staff>
}