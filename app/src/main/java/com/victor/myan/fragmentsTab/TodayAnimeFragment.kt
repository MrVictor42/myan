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
import com.victor.myan.services.interfaces.JikanApiServices
import com.victor.myan.model.Anime
import com.victor.myan.services.impl.AuxServicesImpl
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.awaitResponse

class TodayAnimeFragment : Fragment() {

    private lateinit var todayAnimeAdapter: TodayAnimeAdapter
    private lateinit var binding: FragmentTodayAnimeBinding

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
    ): View? {
        binding = FragmentTodayAnimeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val auxServicesImpl = AuxServicesImpl()
        val api = JikanApiInstance.getJikanApiInstance().create(JikanApiServices::class.java)
        val recyclerView = binding.recyclerTodayAnime
        val animeList = arrayListOf<Anime>()
        val currentDay = auxServicesImpl.getCurrentDay()
        var day: String = ""

        todayAnimeAdapter = TodayAnimeAdapter(animeList)
        recyclerView.adapter = todayAnimeAdapter
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        when(currentDay) {
            1 -> day = DaysEnum.Sunday.name
            2 -> day = DaysEnum.Monday.name
            3 -> day = DaysEnum.Tuesday.name
            4 -> day = DaysEnum.Wednesday.name
            5 -> day = DaysEnum.Thursday.name
            6 -> day = DaysEnum.Friday.name
            7 -> day = DaysEnum.Saturday.name
        }

        CoroutineScope(Dispatchers.IO).launch {
            val call: Call<JsonObject> = api.getTodayAnime(day)
            val response = call.awaitResponse()
            withContext(Dispatchers.Main) {
                if(response.isSuccessful) {
                    val animeResponse = response.body()
                    if(animeResponse != null) {
                        todayAnimeAdapter.items.clear()
                        val day: JsonArray? = animeResponse.getAsJsonArray("monday")
                        if (day != null) {
                            for(anime in 0 until day.size()) {
                                val animeObject: JsonObject? = day.get(anime) as JsonObject?
                                val todayAnime = Anime()
                                if (animeObject != null) {
                                    todayAnime.mal_id = animeObject.get("mal_id").asInt
                                    todayAnime.image_url = animeObject.get("image_url").asString
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
}