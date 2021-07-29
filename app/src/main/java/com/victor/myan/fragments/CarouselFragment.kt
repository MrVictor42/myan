package com.victor.myan.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import com.victor.myan.R
import com.victor.myan.api.CategoryApi
import com.victor.myan.databinding.FragmentCarouselBinding
import com.victor.myan.helper.JikanApiInstanceHelper
import com.victor.myan.modals.AnimeBottomSheetFragment
import com.victor.myan.model.Anime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CarouselFragment : Fragment() {

    private lateinit var binding : FragmentCarouselBinding
    private val limit = 12

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCarouselBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val carouselView = binding.carouselView
        val listAnime : MutableList<Anime> = mutableListOf()
        val listAnimeTitle : MutableList<String> = mutableListOf()
        val bottomSheetFragment = AnimeBottomSheetFragment()
        val bundle = Bundle()

        val api = JikanApiInstanceHelper.getJikanApiInstance().create(CategoryApi::class.java)
        api.slide("airing", "score", limit).enqueue(object :
            Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

            }

            @SuppressLint("InflateParams")
            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                if (response.isSuccessful) {
                    val animeResponse = response.body()
                    if (animeResponse != null) {
                        val results: JsonArray? =
                            animeResponse.getAsJsonArray("results")
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
                                    anime.synopsis = animeFound.get("synopsis").asString

                                    if (animeFound.get("start_date").toString() == "null") {
                                        anime.airing_start = ""
                                    } else {
                                        anime.airing_start = animeFound.get("start_date").asString
                                    }
                                    listAnime.add(anime)
                                    listAnimeTitle.add(anime.title)
                                }
                            }
                            for (anime in 0 until listAnime.size) {
                                carouselView.setViewListener { position ->
                                    val view = layoutInflater.inflate(R.layout.fragment_carousel_custom, null)

                                    val animeTitle = view.findViewById<TextView>(R.id.anime_title_carousel)
                                    val animeImage = view.findViewById<ImageView>(R.id.anime_image_carousel)

                                    Picasso.get().load(listAnime[position].image_url).fit().into(animeImage)
                                    animeTitle.text = listAnimeTitle[position]
                                    view
                                }

                                carouselView.setImageClickListener { position ->
                                    bundle.putString("mal_id", listAnime[position].mal_id)
                                    bundle.putString("title", listAnime[position].title)
                                    bundle.putString("image_url", listAnime[position].image_url)
                                    bundle.putString("airing_start", listAnime[position].airing_start)
                                    bundle.putInt("episodes", listAnime[position].episodes)
                                    bundle.putDouble("score", listAnime[position].score)

                                    bottomSheetFragment.arguments = bundle
                                    bottomSheetFragment.show((context as FragmentActivity).supportFragmentManager, bottomSheetFragment.tag)
                                }
                            }
                            carouselView.pageCount = limit
                        }
                    }
                }
            }
        })
    }
}