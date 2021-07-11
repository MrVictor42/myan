package com.victor.myan.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.victor.myan.R
import com.victor.myan.adapter.ViewPagerAnimeSlideAdapter
import com.victor.myan.api.CategoryApi
import com.victor.myan.databinding.FragmentViewPagerBinding
import com.victor.myan.helper.JikanApiInstanceHelper
import com.victor.myan.model.Anime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewPagerFragment : Fragment() {

    private lateinit var binding : FragmentViewPagerBinding
    private lateinit var viewPagerAnimeSlideAdapter : ViewPagerAnimeSlideAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewPagerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewPagerAnime = binding.viewPagerAnime
        val animeList = arrayListOf<Anime>()
        viewPagerAnimeSlideAdapter = ViewPagerAnimeSlideAdapter(animeList)
        viewPagerAnime.adapter = viewPagerAnimeSlideAdapter

        viewPagerAnime.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                addDots(view, position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        val api = JikanApiInstanceHelper.getJikanApiInstance().create(CategoryApi::class.java)
        api.slide("airing","score", 12).enqueue(object :
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

                                    if(animeFound.get("start_date").toString() == "null") {
                                        anime.airing_start = ""
                                    } else {
                                        anime.airing_start = animeFound.get("start_date").asString
                                    }

                                    viewPagerAnimeSlideAdapter.anime.add(anime)
                                }
                            }
                            viewPagerAnimeSlideAdapter.notifyDataSetChanged()
                            addDots(view)
                        }
                    }
                }
            }
        })
    }

    private fun addDots(view: View, position : Int = 0) {
        val dotsLinear = binding.dots
        dotsLinear.removeAllViews()
        Array(viewPagerAnimeSlideAdapter.anime.size) {
            val textView = TextView(view.context).apply {
                text = "•"
                textSize = 35f
                if(position == it) {
                    setTextColor(ContextCompat.getColor(view.context, R.color.white))
                } else {
                    setTextColor(ContextCompat.getColor(view.context, R.color.gray))
                }
            }
            dotsLinear.addView(textView)
        }
    }
}