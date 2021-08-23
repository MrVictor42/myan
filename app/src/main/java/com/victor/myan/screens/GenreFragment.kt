package com.victor.myan.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
import com.victor.myan.enums.CategoriesEnum
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.model.Anime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GenreFragment : Fragment() {

    private lateinit var binding : FragmentCategoriesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val genreID = arguments?.getInt("mal_id")
        val name = arguments?.getString("name")
        val api = JikanApiInstance.getJikanApiInstance().create(CategoryApi::class.java)
        val toolbar = binding.toolbar
        val highestScoreNull = binding.highestScoreNull
        val upcomingNull = binding.upcomingNull
        val currentlyAiringNull = binding.currentlyAiringNull
        val completedNull = binding.completedNull
        var recyclerView : RecyclerView

        toolbar.toolbar.title = name
        toolbar.toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        toolbar.toolbar.setNavigationOnClickListener {
            val categoriesListFragment = GenresListFragment()
            (view.context as FragmentActivity)
                .supportFragmentManager
                .beginTransaction()
                .remove(this)
                .replace(R.id.content, categoriesListFragment)
                .addToBackStack(null)
                .commit()
        }

        val categories : List<CategoriesEnum> =
            listOf(
                CategoriesEnum.HighestScore, CategoriesEnum.Airing,
                CategoriesEnum.Completed, CategoriesEnum.Upcoming
            )

        categories.forEach {
            when(it) {
                CategoriesEnum.HighestScore -> {
                    recyclerView = binding.recyclerViewByScore
                    buildRecyclerView(
                        recyclerView,
                        api.categoryByScore(genreID!!, CategoriesEnum.Score, CategoriesEnum.Tv),
                        highestScoreNull
                    )
                }
                CategoriesEnum.Airing -> {
                    recyclerView = binding.recyclerViewByAiring
                    buildRecyclerView(
                        recyclerView,
                        api.categoryByAiring(
                            genreID!!, CategoriesEnum.Airing,
                            CategoriesEnum.Score, CategoriesEnum.Tv
                        ),
                        currentlyAiringNull
                    )
                }
                CategoriesEnum.Completed -> {
                    recyclerView = binding.recyclerViewByCompleted
                    buildRecyclerView(
                        recyclerView,
                        api.categoryByCompleted(
                            genreID!!, CategoriesEnum.Completed,
                            CategoriesEnum.Score, CategoriesEnum.Tv
                        ),
                        completedNull
                    )
                }
                CategoriesEnum.Upcoming -> {
                    recyclerView = binding.recyclerViewByUpcoming
                    buildRecyclerView(
                        recyclerView,
                        api.categoryByUpcoming(
                            genreID!!, CategoriesEnum.Upcoming, CategoriesEnum.Tv),
                        upcomingNull
                    )
                }
                else -> {

                }
            }
        }
    }

    private fun buildRecyclerView(
        recyclerView : RecyclerView,
        request : Call<JsonObject>,
        resultNull : TextView
    ) {
        /*
        val animeList = arrayListOf<Anime>()
        recyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        val animeAdapter = AnimeAdapter(animeList)
        recyclerView.adapter = animeAdapter

        request.enqueue(object :
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
                            animeResponse.getAsJsonArray("results")
                        if (results != null && results.size() > 0) {
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
                        } else {
                            resultNull.visibility = View.VISIBLE
                        }
                    }
                }
            }
        })

         */
    }
}