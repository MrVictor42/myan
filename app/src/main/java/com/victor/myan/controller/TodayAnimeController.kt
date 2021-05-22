package com.victor.myan.controller

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.victor.myan.adapter.TodayAnimeAdapter
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.api.services.TodayAnimeServices
import com.victor.myan.enums.DaysEnum
import com.victor.myan.model.Anime
import com.victor.myan.services.impl.AuxServicesImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class TodayAnimeController {

    private lateinit var todayAnimeAdapter: TodayAnimeAdapter
    private val auxServicesImpl = AuxServicesImpl()

    fun makeCall(): MutableList<Anime> {

        val animeList: MutableList<Anime> = mutableListOf()

        val currentDay = when (auxServicesImpl.getCurrentDay()) {
            1 -> DaysEnum.Sunday.name
            2 -> DaysEnum.Monday.name
            3 -> DaysEnum.Tuesday.name
            4 -> DaysEnum.Wednesday.name
            5 -> DaysEnum.Thursday.name
            6 -> DaysEnum.Friday.name
            7 -> DaysEnum.Saturday.name
            else -> DaysEnum.Sunday.name
        }

        val api = JikanApiInstance.getJikanApiInstance().create(TodayAnimeServices::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response = api.getTodayAnime(currentDay.toLowerCase()).awaitResponse()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val animeResponse = response.body()
                    if (animeResponse != null) {
                        val dayAnime: JsonArray? =
                            animeResponse.getAsJsonArray(currentDay.toLowerCase())
                        if (dayAnime != null) {
                            for (anime in 0 until dayAnime.size()) {
                                val animeObject: JsonObject? = dayAnime.get(anime) as JsonObject?
                                if (animeObject != null) {
                                    val animeToday = Anime()

                                    animeToday.title = animeObject.get("title").asString
                                    animeToday.mal_id = animeObject.get("mal_id").asInt.toString()
                                    animeToday.image_url = animeObject.get("image_url").asString
                                    animeList.add(animeToday)
                                }
                            }
                        }
                    }
                }
            }
        }
        return animeList
    }
}


//        val api = JikanApiInstance.getJikanApiInstance().create(TodayAnimeServices::class.java)
//        api.getTodayAnime(currentDay.toLowerCase()).enqueue(object : Callback<JsonObject> {
//            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
//
//            }
//
//            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
//                if (response.isSuccessful) {
//                    val animeResponse = response.body()
//                    if (animeResponse != null) {
//                        val dayAnime: JsonArray? =
//                            animeResponse.getAsJsonArray(currentDay.toLowerCase())
//                        if (dayAnime != null) {
//                            for (anime in 0 until dayAnime.size()) {
//                                val animeObject: JsonObject? = dayAnime.get(anime) as JsonObject?
//                                if (animeObject != null) {
//                                    val animeToday = Anime()
//
//                                    animeToday.title = animeObject.get("title").asString
//                                    animeToday.mal_id = animeObject.get("mal_id").asInt.toString()
//                                    animeToday.image_url = animeObject.get("image_url").asString
//                                    animeList.add(animeToday)
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        })

