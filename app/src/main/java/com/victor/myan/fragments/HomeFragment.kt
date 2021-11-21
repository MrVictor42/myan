package com.victor.myan.fragments

import android.R.attr
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.victor.myan.adapter.AnimeHorizontalAdapter
import com.victor.myan.adapter.MangaHorizontalAdapter
import com.victor.myan.adapter.AnimeRecyclerViewVerticalAdapter
import com.victor.myan.databinding.FragmentHomeBinding
import com.victor.myan.helper.AuxFunctionsHelper
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Anime
import com.victor.myan.model.Categories
import com.victor.myan.viewmodel.AnimeViewModel
import com.victor.myan.viewmodel.MangaViewModel
import java.io.File
import java.util.Locale
import android.R.attr.path
import android.os.Environment


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerViewVerticalAdapter: AnimeRecyclerViewVerticalAdapter
    val animeList : MutableList<Anime> = mutableListOf()

    private lateinit var animeHorizontalAdapter : AnimeHorizontalAdapter
    private lateinit var mangaHorizontalAdapter: MangaHorizontalAdapter
    private val animeViewModel by lazy {
        ViewModelProvider(this)[AnimeViewModel::class.java]
    }
    private val mangaViewModel by lazy {
        ViewModelProvider(this)[MangaViewModel::class.java]
    }
    private lateinit var frameLoading : FrameLayout
    private lateinit var frameHome : FrameLayout
    private val auxFunctionsHelper = AuxFunctionsHelper()
    private lateinit var recyclerViewAnime : RecyclerView
    private val currentYear = auxFunctionsHelper.getCurrentYear()
    private val currentDay = auxFunctionsHelper.getCurrentDay().lowercase(Locale.getDefault())
    private val currentSeason = auxFunctionsHelper.getSeason().lowercase(Locale.getDefault())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

//    activity?.runOnUiThread {
//        recyclerViewVerticalAdapter = AnimeRecyclerViewVerticalAdapter()
//        recyclerViewVerticalAdapter.setData(animeList)
//        recyclerViewAnime.adapter = recyclerViewVerticalAdapter
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerViewAnime = binding.recyclerViewAnime

//        recyclerViewVerticalAdapter = AnimeRecyclerViewVerticalAdapter()
//        recyclerViewAnime.adapter = recyclerViewVerticalAdapter

//        frameLoading = binding.frameLoading
//        frameHome = binding.frameContent
//
        processAnimeListTodayResponse()
//        processAnimeListAiringResponse()
//        Handler(Looper.getMainLooper()).postDelayed({
//            processMangaListAiringResponse()
//        }, 2000)
//        Handler(Looper.getMainLooper()).postDelayed({
//            processAnimeListSeasonResponse()
//        },1500)
    }

    private fun processAnimeListTodayResponse() {
        var gson = Gson()
        animeViewModel.getAnimeListTodayApi(currentDay)
        animeViewModel.animeListToday.observe(viewLifecycleOwner, { animeToday ->
            when(animeToday) {
                is ScreenStateHelper.Loading -> {

                }
                is ScreenStateHelper.Success -> {
                    if(animeToday.data != null) {
                        val category = Categories()
                        val animeList : MutableList<Anime> = mutableListOf()

                        category.type = "anime"
                        category.title = animeViewModel.currentDayFormatted
                        category.categories.addAll(animeToday.data)


//                        val gsonPretty = GsonBuilder().setPrettyPrinting().create()
                        val jsonString = Gson().toJson(category.toString())
                        Log.e("json", jsonString)
////                        Log.e("JSON:", JSON)
                        activity?.runOnUiThread {
                            recyclerViewVerticalAdapter = AnimeRecyclerViewVerticalAdapter()
                            recyclerViewVerticalAdapter.setData(animeToday.data)
                            recyclerViewAnime.adapter = recyclerViewVerticalAdapter
                        }
                    }
                }
                is ScreenStateHelper.Error -> {
                    processAnimeListTodayResponse()
                }
                else -> {

                }
            }
        })
    }

    private fun processAnimeListAiringResponse() {
        animeViewModel.getAnimeListAiringApi()
        animeViewModel.animeListAiring.observe(viewLifecycleOwner, { airingAnime ->
            when(airingAnime) {
                is ScreenStateHelper.Loading -> {

                }
                is ScreenStateHelper.Success -> {
                    if(airingAnime.data != null) {
                        animeList.clear()
//                        animeList.addAll(airingAnime.data)

                    }
                }
                is ScreenStateHelper.Error -> {
                    processAnimeListAiringResponse()
                }
                else -> {

                }
            }
        })
    }
//
//    private fun processMangaListAiringResponse() {
//        val mangaListAiringText = binding.mangaAiringText
//        val mangaListAiringRecyclerView = binding.mangaAiringRecyclerView
//
//        mangaListAiringText.visibility = View.GONE
//        mangaViewModel.getMangaListAiringApi()
//        mangaViewModel.mangaListAiring.observe(viewLifecycleOwner, { mangaAiring ->
//            when(mangaAiring) {
//                is ScreenStateHelper.Loading -> {
//
//                }
//                is ScreenStateHelper.Success -> {
//                    if(mangaAiring.data != null) {
//                        val mangaList = mangaAiring.data
//                        mangaListAiringRecyclerView.layoutManager =
//                            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
//                        mangaHorizontalAdapter = MangaHorizontalAdapter()
//                        mangaHorizontalAdapter.setData(mangaList)
//                        mangaListAiringRecyclerView.adapter = mangaHorizontalAdapter
//                        mangaListAiringText.text = getString(R.string.manga_airing)
//                        mangaListAiringText.visibility = View.VISIBLE
//                        mangaListAiringRecyclerView.visibility = View.VISIBLE
//
//                        frameHome.visibility = View.VISIBLE
//                    }
//                }
//                is ScreenStateHelper.Error -> {
//                    processMangaListAiringResponse()
//                }
//                else -> {
//
//                }
//            }
//        })
//    }
//
    private fun processAnimeListSeasonResponse() {
        animeViewModel.getAnimeListSeasonApi(currentYear, currentSeason)
        animeViewModel.animeListSeason.observe(viewLifecycleOwner, { seasonAnime ->
            when(seasonAnime) {
                is ScreenStateHelper.Loading -> {

                }
                is ScreenStateHelper.Success -> {
                    if(seasonAnime.data != null) {
                        animeList.clear()
                        animeList.addAll(seasonAnime.data)
                        Log.e("anime", animeList.toString())
                            activity?.runOnUiThread {
        recyclerViewVerticalAdapter = AnimeRecyclerViewVerticalAdapter()
        recyclerViewVerticalAdapter.setData(animeList)
        recyclerViewAnime.adapter = recyclerViewVerticalAdapter
    }
                    }
                }
                is ScreenStateHelper.Error -> {
                    processAnimeListSeasonResponse()
                }
                else -> {

                }
            }
        })
    }
}