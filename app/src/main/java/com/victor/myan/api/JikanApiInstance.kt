package com.victor.myan.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JikanApiInstance {

    companion object {
        private const val BaseURL = "https://api.jikan.moe/v3/"

        fun getJikanApiInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}