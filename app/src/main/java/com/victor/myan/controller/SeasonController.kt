package com.victor.myan.controller

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.victor.myan.R
import com.victor.myan.adapter.AnimeAdapter
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.api.SeasonApi
import com.victor.myan.enums.TypesRequest
import com.victor.myan.model.Anime
import com.victor.myan.services.impl.AuxServicesImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SeasonController {

    private lateinit var animeAdapter: AnimeAdapter
    private val auxServicesImpl = AuxServicesImpl()

    fun getSeasonAnime(view: View) {

        val currentSeason = auxServicesImpl.getSeason()
        val currentYear = auxServicesImpl.getCurrentYear()
        val animeList = arrayListOf<Anime>()
        val seasonAnimeText = view.findViewById<TextView>(R.id.season_anime_textView)
        val recyclerViewSeason = view.findViewById<RecyclerView>(R.id.recyclerViewSeason)

        seasonAnimeText.text = auxServicesImpl.capitalize("season $currentSeason")
        recyclerViewSeason.layoutManager =
            LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
        animeAdapter = AnimeAdapter(animeList)
        recyclerViewSeason.adapter = animeAdapter

        val api = JikanApiInstance.getJikanApiInstance().create(SeasonApi::class.java)
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