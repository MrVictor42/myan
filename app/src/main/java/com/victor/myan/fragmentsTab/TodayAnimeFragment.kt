package com.victor.myan.fragmentsTab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.victor.myan.adapter.TodayAnimeAdapter
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.databinding.FragmentTodayAnimeBinding
import com.victor.myan.enums.DaysEnum
import com.victor.myan.api.JikanApiServices
import com.victor.myan.model.Anime
import com.victor.myan.services.impl.AuxServicesImpl
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.awaitResponse

class TodayAnimeFragment : Fragment() {

    private lateinit var todayAnimeAdapter: TodayAnimeAdapter
    private var _binding: FragmentTodayAnimeBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(): TodayAnimeFragment {
            val todayAnimeFragment = TodayAnimeFragment()
            val args = Bundle()
            todayAnimeFragment.arguments = args
            return todayAnimeFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodayAnimeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val auxServicesImpl = AuxServicesImpl()
        val api = JikanApiInstance.getJikanApiInstance().create(JikanApiServices::class.java)
        val recyclerView = binding.recyclerTodayAnime
        val animeList = arrayListOf<Anime>()
        val day = auxServicesImpl.getCurrentDay()
        var currentDay: String = ""

        todayAnimeAdapter = TodayAnimeAdapter(animeList)
        recyclerView.adapter = todayAnimeAdapter
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        when(day) {
            1 -> currentDay = DaysEnum.Sunday.name
            2 -> currentDay = DaysEnum.Monday.name
            3 -> currentDay = DaysEnum.Tuesday.name
            4 -> currentDay = DaysEnum.Wednesday.name
            5 -> currentDay = DaysEnum.Thursday.name
            6 -> currentDay = DaysEnum.Friday.name
            7 -> currentDay = DaysEnum.Saturday.name
        }

        CoroutineScope(Dispatchers.IO).launch {
            val call: Call<JsonObject> = api.getTodayAnime(currentDay.toLowerCase())
            val response = call.awaitResponse()
            withContext(Dispatchers.Main) {
                if(response.isSuccessful) {
                    val animeResponse = response.body()
                    if(animeResponse != null) {
                        todayAnimeAdapter.items.clear()
                        val day: JsonArray? = animeResponse.getAsJsonArray(currentDay.toLowerCase())
                        if (day != null) {
                            for(anime in 0 until day.size()) {
                                val animeObject: JsonObject? = day.get(anime) as JsonObject?
                                val todayAnime = Anime()
                                if (animeObject != null) {
                                    todayAnime.mal_id = animeObject.get("mal_id").asInt.toString()
                                    todayAnime.image_url = animeObject.get("image_url").asString
                                    todayAnime.title = animeObject.get("title").asString
                                    todayAnime.synopsis = animeObject.get("synopsis").asString
                                    todayAnime.airing_start = animeObject.get("airing_start").asString
                                    todayAnime.synopsis = animeObject.get("synopsis").asString

                                    if(animeObject.get("episodes").toString().isEmpty() ||
                                        animeObject.get("episodes").toString() == "null") {
                                        todayAnime.episodes = 0
                                    } else {
                                        todayAnime.episodes = animeObject.get("episodes").asInt
                                    }

                                    if(animeObject.get("score").toString().isEmpty() ||
                                            animeObject.get("score").toString() == "null") {
                                        todayAnime.score = 0.0
                                    } else {
                                        todayAnime.score = animeObject.get("score").asDouble
                                    }

                                    todayAnimeAdapter.items.add(todayAnime)
                                }
                            }
                            todayAnimeAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}