package com.victor.myan.helper

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JikanApiInstanceHelper {

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