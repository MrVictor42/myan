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
import com.victor.myan.api.TodayAnimeServices
import com.victor.myan.model.Anime
import com.victor.myan.services.impl.AuxServicesImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodayAnimeController {

    private lateinit var animeAdapter: AnimeAdapter
    private val auxServicesImpl = AuxServicesImpl()

    fun getTodayAnime(view : View) {

        val currentDay = auxServicesImpl.getCurrentDay()
        val animeList = arrayListOf<Anime>()
        val todayAnimeText = view.findViewById<TextView>(R.id.today_anime_textView)
        val recyclerViewTodayAnime = view.findViewById<RecyclerView>(R.id.recyclerViewToday)

        todayAnimeText.text = auxServicesImpl.capitalize("today anime: $currentDay")
        recyclerViewTodayAnime.layoutManager =
            LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
        animeAdapter = AnimeAdapter(animeList)
        recyclerViewTodayAnime.adapter = animeAdapter

        val api = JikanApiInstance.getJikanApiInstance().create(TodayAnimeServices::class.java)
        api.getTodayAnime(currentDay).enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if(response.isSuccessful) {
                    val animeResponse = response.body()
                    animeAdapter.anime.clear()
                    if(animeResponse != null) {
                        val dayAnime: JsonArray? = animeResponse.getAsJsonArray(currentDay)
                        if (dayAnime != null) {
                            for(anime in 0 until dayAnime.size()) {
                                val animeObject: JsonObject? = dayAnime.get(anime) as JsonObject?
                                if (animeObject != null) {
                                    val animeToday = Anime()

                                    animeToday.title = animeObject.get("title").asString
                                    animeToday.mal_id = animeObject.get("mal_id").asInt.toString()
                                    animeToday.image_url = animeObject.get("image_url").asString
                                    animeAdapter.anime.add(animeToday)
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