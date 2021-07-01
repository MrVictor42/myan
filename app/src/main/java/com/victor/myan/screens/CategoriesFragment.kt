package com.victor.myan.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.victor.myan.R
import com.victor.myan.databinding.FragmentCategoriesBinding

class CategoriesFragment : Fragment() {

    private lateinit var binding : FragmentCategoriesBinding

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

        val toolbar = binding.toolbar
        toolbar.toolbar.title = type
        toolbar.toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        toolbar.toolbar.setNavigationOnClickListener {
            val categoriesListFragment = CategoriesListFragment()
            (view.context as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.content, categoriesListFragment).addToBackStack(null).commit()
        }

        if (genreID != null) {

        }

        /*

        fun getCategoryByUpcoming(view : View, genreID : Int) {
        val animeList = arrayListOf<Anime>()
        val recyclerViewByUpcoming = view.findViewById<RecyclerView>(R.id.recyclerViewByUpcoming)
        recyclerViewByUpcoming.layoutManager =
            LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
        animeAdapter = AnimeAdapter(animeList)
        recyclerViewByUpcoming.adapter = animeAdapter

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
         */
    }
}