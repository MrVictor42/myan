package com.victor.myan.controller

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.victor.myan.R
import com.victor.myan.adapter.AnimeAdapter
import com.victor.myan.api.AnimeApi
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.enums.TypesRequest
import com.victor.myan.model.Anime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopAnimeController {

    private lateinit var animeAdapter: AnimeAdapter

    fun getTopAnime(view : View) {

        val animeList = arrayListOf<Anime>()
        val recyclerViewTopAnime = view.findViewById<RecyclerView>(R.id.recyclerViewTopAnime)

        recyclerViewTopAnime.layoutManager =
            LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
        animeAdapter = AnimeAdapter(animeList)
        recyclerViewTopAnime.adapter = animeAdapter

        val api = JikanApiInstance.getJikanApiInstance().create(AnimeApi::class.java)
        api.getTopAnime().enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if(response.isSuccessful) {
                    val animeResponse = response.body()
                    animeAdapter.anime.clear()
                    if(animeResponse != null) {
                        val topAnime: JsonArray? = animeResponse.getAsJsonArray(TypesRequest.Top.type)
                        if (topAnime != null) {
                            for(anime in 0 until topAnime.size()) {
                                val animeObject: JsonObject? = topAnime.get(anime) as JsonObject?
                                if (animeObject != null) {
                                    val animeTop = Anime()

                                    animeTop.mal_id = animeObject.get("mal_id").asInt.toString()
                                    animeTop.image_url = animeObject.get("image_url").asString
                                    animeTop.title = animeObject.get("title").asString
                                    animeTop.synopsis = "null"

                                    if(animeObject.get("start_date").toString().isEmpty() ||
                                        animeObject.get("start_date").toString() == "null") {
                                        animeTop.start_date = "null"
                                    } else {
                                        animeTop.start_date = animeObject.get("start_date").asString
                                    }

                                    if(animeObject.get("episodes").toString().isEmpty() ||
                                        animeObject.get("episodes").toString() == "null") {
                                        animeTop.episodes = 0
                                    } else {
                                        animeTop.episodes = animeObject.get("episodes").asInt
                                    }

                                    if(animeObject.get("score").toString().isEmpty() ||
                                        animeObject.get("score").toString() == "null") {
                                        animeTop.score = 0.0
                                    } else {
                                        animeTop.score = animeObject.get("score").asDouble
                                    }

                                    animeAdapter.anime.add(animeTop)
                                }
                            }
                            animeAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        })
    }
}