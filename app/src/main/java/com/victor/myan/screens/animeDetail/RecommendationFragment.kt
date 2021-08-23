package com.victor.myan.screens.animeDetail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.victor.myan.adapter.AnimeAdapter
import com.victor.myan.api.AnimeApi
import com.victor.myan.databinding.FragmentRecommendationBinding
import com.victor.myan.helper.AuxFunctionsHelper
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.model.Anime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecommendationFragment : Fragment() {

    private lateinit var binding : FragmentRecommendationBinding
    private lateinit var animeAdapter: AnimeAdapter
    private val auxServicesHelper = AuxFunctionsHelper()

    companion object {
        fun newInstance(mal_id : String): RecommendationFragment {
            val recommendationFragment = RecommendationFragment()
            val args = Bundle()
            args.putString("mal_id", mal_id)
            recommendationFragment.arguments = args
            return recommendationFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecommendationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /*
        val malID = arguments?.getString("mal_id").toString()
        val animeList = arrayListOf<Anime>()
        val recommendationRecyclerView = binding.recyclerRecommendations
        val animeApi = JikanApiInstance.getJikanApiInstance().create(AnimeApi::class.java)

        recommendationRecyclerView.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        animeAdapter = AnimeAdapter(animeList)
        recommendationRecyclerView.adapter = animeAdapter

        animeApi.getRecommendations(malID).enqueue(object :
            Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if(response.isSuccessful) {
                    val animeRecommendationResponse = response.body()
                    animeAdapter.anime.clear()
                    if(animeRecommendationResponse != null) {
                        val recommendationArray : JsonArray? = animeRecommendationResponse.getAsJsonArray("recommendations")
                        if(recommendationArray != null) {
                            for(recommendation in 0 until recommendationArray.size()) {
                                val recommendationObject : JsonObject? = recommendationArray.get(recommendation) as JsonObject?
                                if(recommendationObject != null) {
                                    animeApi.getAnime(recommendationObject.get("mal_id").asString).enqueue(object :
                                        Callback<Anime> {
                                        override fun onFailure(call: Call<Anime>, t: Throwable) {

                                        }

                                        @SuppressLint("NotifyDataSetChanged")
                                        override fun onResponse(call: Call<Anime>, response: Response<Anime>) {
                                            if (response.isSuccessful) {
                                                val animeResponseRecommendation = response.body()
                                                if(animeResponseRecommendation != null) {
                                                    val animeRecommendation = Anime()

                                                    animeRecommendation.mal_id = animeResponseRecommendation.mal_id
                                                    animeRecommendation.image_url = animeResponseRecommendation.image_url
                                                    animeRecommendation.title = animeResponseRecommendation.title

                                                    if(animeResponseRecommendation.episodes.toString().isNullOrEmpty()) {
                                                        animeRecommendation.episodes = 0
                                                    } else {
                                                        animeRecommendation.episodes = animeResponseRecommendation.episodes
                                                    }

                                                    if(animeResponseRecommendation.premiered.isNullOrEmpty()) {
                                                        animeRecommendation.premiered = ""
                                                    } else {
                                                        animeRecommendation.premiered = auxServicesHelper.formatPremiered(animeResponseRecommendation.premiered)
                                                    }

                                                    if(animeResponseRecommendation.score.toString().isNullOrEmpty()) {
                                                        animeRecommendation.score = 0.0
                                                    } else {
                                                        animeRecommendation.score = animeResponseRecommendation.score
                                                    }

                                                    animeAdapter.anime.add(animeRecommendation)
                                                }
                                                animeAdapter.notifyDataSetChanged()
                                            }
                                        }
                                    })
                                }
                            }
                        }
                    }
                }
            }
        })

         */
    }
}