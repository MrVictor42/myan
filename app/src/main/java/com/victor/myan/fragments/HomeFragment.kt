package com.victor.myan.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.victor.myan.R
import com.victor.myan.adapter.TodayAnimeAdapter
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.api.services.JikanApiServices
import com.victor.myan.databinding.FragmentHomeBinding
import com.victor.myan.enums.DaysEnum
import com.victor.myan.model.Anime
import com.victor.myan.services.impl.AuxServicesImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val auxServicesImpl = AuxServicesImpl()
    private lateinit var todayAnimeAdapter: TodayAnimeAdapter

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
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val currentDay = when(auxServicesImpl.getCurrentDay()) {
            1 -> DaysEnum.Sunday.name
            2 -> DaysEnum.Monday.name
            3 -> DaysEnum.Tuesday.name
            4 -> DaysEnum.Wednesday.name
            5 -> DaysEnum.Thursday.name
            6 -> DaysEnum.Friday.name
            7 -> DaysEnum.Saturday.name
            else -> DaysEnum.Sunday.name
        }

        val animeList = arrayListOf<Anime>()
        val animeToday = Anime()
        val todayAnime : MutableList<Anime> = mutableListOf()
        val todayAnimeText = binding.todayAnimeTextView

        todayAnimeText.text = auxServicesImpl.capitalize("today anime: $currentDay")

        val recyclerViewHome = view.findViewById<RecyclerView>(R.id.recyclerViewHome)
        recyclerViewHome.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        todayAnimeAdapter = TodayAnimeAdapter(animeList)
        recyclerViewHome.adapter = todayAnimeAdapter

        val api = JikanApiInstance.getJikanApiInstance().create(JikanApiServices::class.java)
        api.getTodayAnime(currentDay.toLowerCase()).enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if(response.isSuccessful) {
                    val animeResponse = response.body()
                    todayAnimeAdapter.anime.clear()
                    if(animeResponse != null) {
                        val dayAnime: JsonArray? = animeResponse.getAsJsonArray(currentDay.toLowerCase())
                        if (dayAnime != null) {
                            todayAnime.clear()
                            for(anime in 0 until dayAnime.size()) {
                                val animeObject: JsonObject? = dayAnime.get(anime) as JsonObject?
                                if (animeObject != null) {
                                    animeToday.mal_id = animeObject.get("mal_id").asInt.toString()
                                    animeToday.image_url = animeObject.get("image_url").asString
                                    todayAnime.add(animeToday)
                                    todayAnimeAdapter.anime.addAll(todayAnime)
                                }
                            }
                            todayAnimeAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}