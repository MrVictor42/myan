package com.victor.myan.controller

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.victor.myan.R
import com.victor.myan.adapter.AnimeAdapter
import com.victor.myan.api.CategoryApi
import com.victor.myan.enums.TypesEnum
import com.victor.myan.helper.JikanApiInstanceHelper
import com.victor.myan.model.Anime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryByCompleted {

    private lateinit var animeAdapter: AnimeAdapter

    fun getCategoryByScore(view : View, genreID : Int) {
        val animeList = arrayListOf<Anime>()
        val recyclerViewByCompleted = view.findViewById<RecyclerView>(R.id.recyclerViewByCompleted)
        recyclerViewByCompleted.layoutManager =
            LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
        animeAdapter = AnimeAdapter(animeList)
        recyclerViewByCompleted.adapter = animeAdapter

        val api = JikanApiInstanceHelper.getJikanApiInstance().create(CategoryApi::class.java)
        api.categoryByCompleted(genreID, "completed", "score", "tv").enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                if (response.isSuccessful) {
                    val animeResponse = response.body()
                    animeAdapter.anime.clear()
                    if (animeResponse != null) {
                        val results: JsonArray? =
                            animeResponse.getAsJsonArray(TypesEnum.Results.type)
                        if (results != null) {
                            for (result in 0 until results.size()) {
                                val animeFound: JsonObject? =
                                    results.get(result) as JsonObject?
                                if (animeFound != null) {
                                    val anime = Anime()

                                    anime.title = animeFound.get("title").asString
                                    anime.mal_id =
                                        animeFound.get("mal_id").asInt.toString()
                                    anime.episodes = animeFound.get("episodes").asInt
                                    anime.image_url =
                                        animeFound.get("image_url").asString
                                    anime.airing_start = animeFound.get("start_date").asString
                                    anime.score = animeFound.get("score").asDouble
                                    animeAdapter.anime.add(anime)
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