package com.victor.myan.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.victor.myan.adapter.AnimeAdapter
import com.victor.myan.api.AnimeApi
import com.victor.myan.databinding.FragmentTodayAnimeBinding
import com.victor.myan.helper.AuxFunctionsHelper
import com.victor.myan.helper.JikanApiInstanceHelper
import com.victor.myan.model.Anime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class TodayAnimeFragment : Fragment() {

    private lateinit var binding : FragmentTodayAnimeBinding
    private lateinit var animeAdapter: AnimeAdapter
    private val auxServicesHelper = AuxFunctionsHelper()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodayAnimeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val currentDay = auxServicesHelper.getCurrentDay().lowercase(Locale.getDefault())
        val animeList = arrayListOf<Anime>()
        val todayAnimeText = binding.todayAnimeText
        val recyclerViewTodayAnime = binding.recyclerViewToday

        todayAnimeText.text = auxServicesHelper.capitalize("today anime: $currentDay")
        recyclerViewTodayAnime.layoutManager =
            LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
        animeAdapter = AnimeAdapter(animeList)
        recyclerViewTodayAnime.adapter = animeAdapter

        val api = JikanApiInstanceHelper.getJikanApiInstance().create(AnimeApi::class.java)
        api.getTodayAnime(currentDay).enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

            }

            @SuppressLint("NotifyDataSetChanged")
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

                                    animeToday.mal_id = animeObject.get("mal_id").asInt.toString()
                                    animeToday.title = animeObject.get("title").asString
                                    animeToday.image_url = animeObject.get("image_url").asString
                                    animeToday.synopsis = animeObject.get("synopsis").asString

                                    if(animeObject.get("airing_start").toString().isEmpty() ||
                                        animeObject.get("airing_start").toString() == "null") {
                                        animeToday.airing_start = ""
                                    } else {
                                        animeToday.airing_start = animeObject.get("airing_start").asString
                                    }

                                    if(animeObject.get("episodes").toString().isEmpty() ||
                                        animeObject.get("episodes").toString() == "null") {
                                        animeToday.episodes = 0
                                    } else {
                                        animeToday.episodes = animeObject.get("episodes").asInt
                                    }

                                    if(animeObject.get("score").toString().isEmpty() ||
                                        animeObject.get("score").toString() == "null") {
                                        animeToday.score = 0.0
                                    } else {
                                        animeToday.score = animeObject.get("score").asDouble
                                    }

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