package com.victor.myan.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victor.myan.R
import com.victor.myan.adapter.AnimeAdapter
import com.victor.myan.adapter.MangaAdapter
import com.victor.myan.databinding.FragmentHomeBinding
import com.victor.myan.helper.AuxFunctionsHelper
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.viewmodel.AnimeViewModel
import com.victor.myan.viewmodel.MangaViewModel
import java.util.Locale

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var animeAdapter : AnimeAdapter
    private lateinit var mangaAdapter : MangaAdapter
    private val animeViewModel by lazy {
        ViewModelProvider(this)[AnimeViewModel::class.java]
    }
    private val mangaViewModel by lazy {
        ViewModelProvider(this)[MangaViewModel::class.java]
    }
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
        processAnimeListTodayResponse()
        processAnimeListAiringResponse()
        Handler(Looper.getMainLooper()).postDelayed({
            processMangaListAiringResponse()
            Handler(Looper.getMainLooper()).postDelayed({
                processAnimeListSeasonResponse()
            },2000)
        }, 3000)
    }

    private fun processAnimeListTodayResponse() {
        val todayAnimeText = binding.todayAnimeText
        val todayAnimeRecyclerView = binding.todayRecyclerView
        val shimmerLayoutToday = binding.shimmerLayoutToday

        animeViewModel.getAnimeListTodayApi(currentDay)
        animeViewModel.animeListToday.observe(viewLifecycleOwner, { animeToday ->
            when(animeToday) {
                is ScreenStateHelper.Loading -> {
                    shimmerLayoutToday.startShimmer()
                }
                is ScreenStateHelper.Success -> {
                    if(animeToday.data != null) {
                        val animeList = animeToday.data
                        todayAnimeRecyclerView.layoutManager =
                            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                        animeAdapter = AnimeAdapter()
                        animeAdapter.setData(animeList)
                        todayAnimeRecyclerView.adapter = animeAdapter
                        shimmerLayoutToday.stopShimmer()
                        shimmerLayoutToday.visibility = View.GONE
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
        val shimmerLayoutAnimeAiring = binding.shimmerLayoutAnimeAiring

        animeViewModel.getAnimeListAiringApi()
        animeViewModel.animeListAiring.observe(viewLifecycleOwner, { airingAnime ->
            when(airingAnime) {
                is ScreenStateHelper.Loading -> {
                    shimmerLayoutAnimeAiring.startShimmer()
                }
                is ScreenStateHelper.Success -> {
                    if(airingAnime.data != null) {
                        val animeList = airingAnime.data
                        airingAnimeRecyclerView.layoutManager =
                            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                        animeAdapter = AnimeAdapter()
                        animeAdapter.setData(animeList)
                        airingAnimeRecyclerView.adapter = animeAdapter
                        shimmerLayoutAnimeAiring.stopShimmer()
                        shimmerLayoutAnimeAiring.visibility = View.GONE
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
        val shimmerLayoutMangaAiring = binding.shimmerLayoutMangaAiring

        mangaListAiringText.visibility = View.GONE
        mangaViewModel.getMangaListAiringApi()
        mangaViewModel.mangaListAiring.observe(viewLifecycleOwner, { mangaAiring ->
            when(mangaAiring) {
                is ScreenStateHelper.Loading -> {
                    shimmerLayoutMangaAiring.startShimmer()
                }
                is ScreenStateHelper.Success -> {
                    if(mangaAiring.data != null) {
                        val mangaList = mangaAiring.data
                        mangaListAiringRecyclerView.layoutManager =
                            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                        mangaAdapter = MangaAdapter()
                        mangaAdapter.setData(mangaList)
                        mangaListAiringRecyclerView.adapter = mangaAdapter
                        shimmerLayoutMangaAiring.stopShimmer()
                        shimmerLayoutMangaAiring.visibility = View.GONE
                        mangaListAiringText.text = getString(R.string.manga_airing)
                        mangaListAiringText.visibility = View.VISIBLE
                        mangaListAiringRecyclerView.visibility = View.VISIBLE
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
        val shimmerLayoutSeason = binding.shimmerLayoutSeason

        seasonAnimeText.visibility = View.GONE
        animeViewModel.getAnimeListSeasonApi(currentYear, currentSeason)
        animeViewModel.animeListSeason.observe(viewLifecycleOwner, { seasonAnime ->
            when(seasonAnime) {
                is ScreenStateHelper.Loading -> {
                    shimmerLayoutSeason.startShimmer()
                }
                is ScreenStateHelper.Success -> {
                    if(seasonAnime.data != null) {
                        val animeList = seasonAnime.data
                        seasonAnimeRecyclerView.layoutManager =
                            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                        animeAdapter = AnimeAdapter()
                        animeAdapter.setData(animeList)
                        seasonAnimeRecyclerView.adapter = animeAdapter
                        shimmerLayoutSeason.stopShimmer()
                        shimmerLayoutSeason.visibility = View.GONE
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