package com.victor.myan.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroInstance {

    companion object {
        private const val BaseURL = "https://api.github.com/search/"

        fun getRetroInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}