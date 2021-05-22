package com.victor.myan.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.victor.myan.R
import com.victor.myan.adapter.TodayAnimeAdapter
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.api.services.JikanApiServices
import com.victor.myan.api.services.TodayAnimeServices
import com.victor.myan.controller.TodayAnimeController
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
    private val api = JikanApiInstance.getJikanApiInstance().create(TodayAnimeServices::class.java)

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
        val todayAnime : MutableList<Anime> = TodayAnimeController().makeCall()
        Log.e("Today: ", todayAnime.toString())

//        val recyclerViewHome = view.findViewById<RecyclerView>(R.id.recyclerViewDay)
//        recyclerViewHome.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
//        todayAnimeAdapter = TodayAnimeAdapter(animeList)
//        recyclerViewHome.adapter = todayAnimeAdapter
//

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}