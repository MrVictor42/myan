package com.victor.myan.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victor.myan.R
import com.victor.myan.adapter.AnimeHorizontalAdapter
import com.victor.myan.adapter.MangaHorizontalAdapter
import com.victor.myan.databinding.FragmentHomeBinding
import com.victor.myan.helper.AuxFunctionsHelper
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.viewmodel.AnimeViewModel
import com.victor.myan.viewmodel.MangaViewModel
import java.util.Locale

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        frameLoading = binding.frameLoading
        frameHome = binding.frameContent

        processAnimeListTodayResponse()
        processAnimeListAiringResponse()
        Handler(Looper.getMainLooper()).postDelayed({
            processMangaListAiringResponse()
        }, 2000)
        Handler(Looper.getMainLooper()).postDelayed({
            processAnimeListSeasonResponse()
        },1500)
    }

    private fun processAnimeListTodayResponse() {
        val todayAnimeText = binding.todayAnimeText
        val todayAnimeRecyclerView = binding.todayRecyclerView

        animeViewModel.getAnimeListTodayApi(currentDay)
        animeViewModel.animeListToday.observe(viewLifecycleOwner, { animeToday ->
            when(animeToday) {
                is ScreenStateHelper.Loading -> {

                }
                is ScreenStateHelper.Success -> {
                    if(animeToday.data != null) {
                        val animeList = animeToday.data
                        todayAnimeRecyclerView.layoutManager =
                            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                        animeHorizontalAdapter = AnimeHorizontalAdapter()
                        animeHorizontalAdapter.setData(animeList)
                        todayAnimeRecyclerView.adapter = animeHorizontalAdapter

                        todayAnimeText.text = animeViewModel.currentDayFormatted
                        todayAnimeText.visibility = View.VISIBLE
                        todayAnimeRecyclerView.visibility = View.VISIBLE
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
        val airingAnimeText = binding.animeAiringText
        val airingAnimeRecyclerView = binding.animeAiringRecyclerView

        animeViewModel.getAnimeListAiringApi()
        animeViewModel.animeListAiring.observe(viewLifecycleOwner, { airingAnime ->
            when(airingAnime) {
                is ScreenStateHelper.Loading -> {

                }
                is ScreenStateHelper.Success -> {
                    if(airingAnime.data != null) {
                        val animeList = airingAnime.data
                        airingAnimeRecyclerView.layoutManager =
                            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                        animeHorizontalAdapter = AnimeHorizontalAdapter()
                        animeHorizontalAdapter.setData(animeList)
                        airingAnimeRecyclerView.adapter = animeHorizontalAdapter

                        frameLoading.visibility = View.GONE
                        airingAnimeText.text = getString(R.string.anime_airing)
                        airingAnimeText.visibility = View.VISIBLE
                        airingAnimeRecyclerView.visibility = View.VISIBLE
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

    private fun processMangaListAiringResponse() {
        val mangaListAiringText = binding.mangaAiringText
        val mangaListAiringRecyclerView = binding.mangaAiringRecyclerView

        mangaListAiringText.visibility = View.GONE
        mangaViewModel.getMangaListAiringApi()
        mangaViewModel.mangaListAiring.observe(viewLifecycleOwner, { mangaAiring ->
            when(mangaAiring) {
                is ScreenStateHelper.Loading -> {

                }
                is ScreenStateHelper.Success -> {
                    if(mangaAiring.data != null) {
                        val mangaList = mangaAiring.data
                        mangaListAiringRecyclerView.layoutManager =
                            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                        mangaHorizontalAdapter = MangaHorizontalAdapter()
                        mangaHorizontalAdapter.setData(mangaList)
                        mangaListAiringRecyclerView.adapter = mangaHorizontalAdapter
                        mangaListAiringText.text = getString(R.string.manga_airing)
                        mangaListAiringText.visibility = View.VISIBLE
                        mangaListAiringRecyclerView.visibility = View.VISIBLE

                        frameHome.visibility = View.VISIBLE
                    }
                }
                is ScreenStateHelper.Error -> {
                    processMangaListAiringResponse()
                }
                else -> {

                }
            }
        })
    }

    private fun processAnimeListSeasonResponse() {
        val seasonAnimeText = binding.seasonText
        val seasonAnimeRecyclerView = binding.seasonRecyclerView

        seasonAnimeText.visibility = View.GONE
        animeViewModel.getAnimeListSeasonApi(currentYear, currentSeason)
        animeViewModel.animeListSeason.observe(viewLifecycleOwner, { seasonAnime ->
            when(seasonAnime) {
                is ScreenStateHelper.Loading -> {

                }
                is ScreenStateHelper.Success -> {
                    if(seasonAnime.data != null) {
                        val animeList = seasonAnime.data
                        seasonAnimeRecyclerView.layoutManager =
                            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                        animeHorizontalAdapter = AnimeHorizontalAdapter()
                        animeHorizontalAdapter.setData(animeList)
                        seasonAnimeRecyclerView.adapter = animeHorizontalAdapter
                        seasonAnimeText.text = animeViewModel.currentSeasonFormatted
                        seasonAnimeText.visibility = View.VISIBLE
                        seasonAnimeRecyclerView.visibility = View.VISIBLE
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