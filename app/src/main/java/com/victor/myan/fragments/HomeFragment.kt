package com.victor.myan.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.victor.myan.adapter.ViewPagerAnimeSlideAdapter
import com.victor.myan.api.CategoryApi
import com.victor.myan.controller.MangaTopController
import com.victor.myan.controller.SeasonController
import com.victor.myan.controller.TodayAnimeController
import com.victor.myan.controller.TopAnimeController
import com.victor.myan.databinding.FragmentHomeBinding
import com.victor.myan.enums.TypesRequest
import com.victor.myan.helper.JikanApiInstanceHelper
import com.victor.myan.model.Anime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewPagerAnimeSlideAdapter : ViewPagerAnimeSlideAdapter
    private val todayAnimeController = TodayAnimeController()
    private val seasonAnimeController = SeasonController()
    private val topAnimeController = TopAnimeController()
    private val mangaTopController = MangaTopController()

    companion object {
        fun newInstance(): HomeFragment {
            val homeFragment = HomeFragment()
            val args = Bundle()
            homeFragment.arguments = args
            return homeFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val viewPagerAnime = binding.viewPagerAnime
        val animeList = arrayListOf<Anime>()
        viewPagerAnimeSlideAdapter = ViewPagerAnimeSlideAdapter(animeList)
        viewPagerAnime.adapter = viewPagerAnimeSlideAdapter

        val api = JikanApiInstanceHelper.getJikanApiInstance().create(CategoryApi::class.java)
        api.categoryByUpcoming(1, "upcoming", "tv").enqueue(object :
            Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                if (response.isSuccessful) {
                    val animeResponse = response.body()
                    viewPagerAnimeSlideAdapter.anime.clear()
                    if (animeResponse != null) {
                        val results: JsonArray? =
                            animeResponse.getAsJsonArray(TypesRequest.Results.type)
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
                                    anime.score = animeFound.get("score").asDouble

                                    if(animeFound.get("start_date").toString() == "null") {
                                        anime.airing_start = ""
                                    } else {
                                        anime.airing_start = animeFound.get("start_date").asString
                                    }

                                    viewPagerAnimeSlideAdapter.anime.add(anime)
                                }
                            }
                            viewPagerAnimeSlideAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        })


//        todayAnimeController.getTodayAnime(view)
//        seasonAnimeController.getSeasonAnime(view)
//        topAnimeController.getTopAnime(view)
//        mangaTopController.getTopManga(view)
    }

    /*
    val api = JikanApiInstanceHelper.getJikanApiInstance().create(CategoryApi::class.java)
        api.categoryByUpcoming(genreID, "upcoming", "tv").enqueue(object :
            Callback<JsonObject> {
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
                            animeResponse.getAsJsonArray(TypesRequest.Results.type)
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
                                    anime.score = animeFound.get("score").asDouble

                                    if(animeFound.get("start_date").toString() == "null") {
                                        anime.airing_start = ""
                                    } else {
                                        anime.airing_start = animeFound.get("start_date").asString
                                    }

                                    animeAdapter.anime.add(anime)
                                }
                            }
                            animeAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        })
     */
}