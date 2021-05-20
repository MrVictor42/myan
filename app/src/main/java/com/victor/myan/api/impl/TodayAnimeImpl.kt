package com.victor.myan.api.impl

import android.util.Log
import com.google.gson.JsonArray
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.api.services.JikanApiServices
import com.victor.myan.api.services.TodayAnimeServices
import com.google.gson.JsonObject
import com.victor.myan.model.Anime
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.awaitResponse

class TodayAnimeImpl : TodayAnimeServices {

    private val api = JikanApiInstance.getJikanApiInstance().create(JikanApiServices::class.java)

    override fun getTodayAnime(day: String) : MutableList<Anime> {

        val todayAnime : MutableList<Anime> = mutableListOf()
        val animeToday = Anime()

//        CoroutineScope(Dispatchers.IO).launch {
//            val call : Call<JsonObject> = api.getTodayAnime(day)
//            val response = call.awaitResponse()
//            withContext(Dispatchers.Main) {
//                if (response.isSuccessful) {
//                    val animeResponse = response.body()
//                    if(animeResponse != null) {
//                        val dayAnime: JsonArray? = animeResponse.getAsJsonArray(day)
//                        if (dayAnime != null) {
//                            todayAnime.clear()
//                            for(anime in 0 until dayAnime.size()) {
//                                val animeObject: JsonObject? = dayAnime.get(anime) as JsonObject?
//                                if (animeObject != null) {
//                                    animeToday.mal_id = animeObject.get("mal_id").asInt.toString()
//                                    animeToday.image_url = animeObject.get("image_url").asString
//                                    animeToday.title = animeObject.get("title").asString
//                                    animeToday.synopsis = animeObject.get("synopsis").asString
//                                    animeToday.airing_start = animeObject.get("airing_start").asString
//                                    animeToday.synopsis = animeObject.get("synopsis").asString
//
//                                    if(animeObject.get("episodes").toString().isEmpty() ||
//                                        animeObject.get("episodes").toString() == "null") {
//                                        animeToday.episodes = 0
//                                    } else {
//                                        animeToday.episodes = animeObject.get("episodes").asInt
//                                    }
//
//                                    if(animeObject.get("score").toString().isEmpty() ||
//                                        animeObject.get("score").toString() == "null") {
//                                        animeToday.score = 0.0
//                                    } else {
//                                        animeToday.score = animeObject.get("score").asDouble
//                                    }
//                                    todayAnime.add(animeToday)
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            delay(2000)
//        }
        return todayAnime
    }
}