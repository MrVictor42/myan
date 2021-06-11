package com.victor.myan.controller

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.victor.myan.R
import com.victor.myan.adapter.AnimeAdapter
import com.victor.myan.api.AnimeApi
import com.victor.myan.helper.JikanApiInstanceHelper
import com.victor.myan.enums.TypesRequest
import com.victor.myan.model.Anime
import com.victor.myan.helper.AuxFunctionsHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SeasonController {

    private lateinit var animeAdapter: AnimeAdapter
    private val auxServicesHelper = AuxFunctionsHelper()

    fun getSeasonAnime(view: View) {

        val currentSeason = auxServicesHelper.getSeason()
        val currentYear = auxServicesHelper.getCurrentYear()
        val animeList = arrayListOf<Anime>()
        val seasonAnimeText = view.findViewById<TextView>(R.id.season_anime_textView)
        val recyclerViewSeason = view.findViewById<RecyclerView>(R.id.recyclerViewSeason)

        seasonAnimeText.text = auxServicesHelper.capitalize("season $currentSeason")
        recyclerViewSeason.layoutManager =
            LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
        animeAdapter = AnimeAdapter(animeList)
        recyclerViewSeason.adapter = animeAdapter

        val api = JikanApiInstanceHelper.getJikanApiInstance().create(AnimeApi::class.java)
        api.getCurrentSeason(currentYear,currentSeason).enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if(response.isSuccessful) {
                    val animeResponse = response.body()
                    animeAdapter.anime.clear()
                    if(animeResponse != null) {
                        val seasonAnime: JsonArray? = animeResponse.getAsJsonArray(TypesRequest.Anime.type)
                        if (seasonAnime != null) {
                            for(anime in 0 until seasonAnime.size()) {
                                val animeObject: JsonObject? = seasonAnime.get(anime) as JsonObject?
                                if (animeObject != null) {
                                    val animeSeason = Anime()

                                    animeSeason.title = animeObject.get("title").asString
                                    animeSeason.mal_id = animeObject.get("mal_id").asInt.toString()
                                    animeSeason.image_url = animeObject.get("image_url").asString
                                    animeSeason.synopsis = animeObject.get("synopsis").asString

                                    if(animeObject.get("airing_start").toString().isEmpty() ||
                                        animeObject.get("airing_start").toString() == "null") {
                                        animeSeason.airing_start = ""
                                    } else {
                                        animeSeason.airing_start = animeObject.get("airing_start").asString
                                    }

                                    if(animeObject.get("episodes").toString().isEmpty() ||
                                        animeObject.get("episodes").toString() == "null") {
                                        animeSeason.episodes = 0
                                    } else {
                                        animeSeason.episodes = animeObject.get("episodes").asInt
                                    }

                                    if(animeObject.get("score").toString().isEmpty() ||
                                        animeObject.get("score").toString() == "null") {
                                        animeSeason.score = 0.0
                                    } else {
                                        animeSeason.score = animeObject.get("score").asDouble
                                    }

                                    animeAdapter.anime.add(animeSeason)
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