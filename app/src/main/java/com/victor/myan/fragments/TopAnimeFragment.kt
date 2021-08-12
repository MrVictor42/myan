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
import com.victor.myan.databinding.FragmentTopAnimeBinding
import com.victor.myan.helper.AuxFunctionsHelper
import com.victor.myan.helper.JikanApiInstanceHelper
import com.victor.myan.model.Anime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopAnimeFragment : Fragment() {

    private lateinit var binding : FragmentTopAnimeBinding
    private lateinit var animeAdapter: AnimeAdapter
    private val auxFunctionsHelper = AuxFunctionsHelper()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopAnimeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val animeList = arrayListOf<Anime>()
        val recyclerViewTopAnime = binding.recyclerViewTopAnime

        recyclerViewTopAnime.layoutManager =
            LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
        animeAdapter = AnimeAdapter(animeList)
        recyclerViewTopAnime.adapter = animeAdapter

        val api = JikanApiInstanceHelper.getJikanApiInstance().create(AnimeApi::class.java)
        api.getTopAnime().enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if(response.isSuccessful) {
                    val animeResponse = response.body()
                    animeAdapter.anime.clear()
                    if(animeResponse != null) {
                        val topAnime: JsonArray? = animeResponse.getAsJsonArray("top")
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
                                        animeTop.airing_start = ""
                                    } else {
                                        animeTop.airing_start = auxFunctionsHelper.formatYear(animeObject.get("start_date").asString)
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