package com.victor.myan.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.victor.myan.R
import com.victor.myan.adapter.AnimeAdapter
import com.victor.myan.api.CategoryApi
import com.victor.myan.databinding.FragmentCategoriesBinding
import com.victor.myan.enums.TypesEnum
import com.victor.myan.helper.JikanApiInstanceHelper
import com.victor.myan.model.Anime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesFragment : Fragment() {

    private lateinit var binding : FragmentCategoriesBinding
    private lateinit var animeAdapter : AnimeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val genreID = arguments?.getInt("genre")
        val type = arguments?.getString("type")
        val animeList = arrayListOf<Anime>()
        var recyclerView : RecyclerView
        val api = JikanApiInstanceHelper.getJikanApiInstance().create(CategoryApi::class.java)
        var apiRequest : Call<JsonObject>

        val toolbar = binding.toolbar
        toolbar.toolbar.title = type
        toolbar.toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        toolbar.toolbar.setNavigationOnClickListener {
            val categoriesListFragment = CategoriesListFragment()
            (view.context as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.content, categoriesListFragment).addToBackStack(null).commit()
        }

        val categories : List<String> = listOf("Highest Score", "Airing", "Completed", "Upcoming")

        categories.forEach {
            when(it) {
                "Highest Score" -> {
                    recyclerView = binding.recyclerViewByScore
                    recyclerView.layoutManager =
                        LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
                    animeAdapter = AnimeAdapter(animeList)
                    recyclerView.adapter = animeAdapter
                    if (genreID != null) {
                        api.categoryByScore(genreID, "score", "tv").enqueue(object :
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
                    }
                }
                "Airing" -> {

                }
            }
        }
    }
}