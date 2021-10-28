package com.victor.myan.fragments

import android.os.Bundle
import android.util.Log
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
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var animeAdapter: AnimeAdapter
    private lateinit var mangaAdapter: MangaAdapter
    private val TAG = HomeFragment::class.java.simpleName
    private val animeViewModel by lazy {
        ViewModelProvider(this).get(AnimeViewModel::class.java)
    }
    private val mangaViewModel by lazy {
        ViewModelProvider(this).get(MangaViewModel::class.java)
    }
    private val auxFunctionsHelper = AuxFunctionsHelper()
    private val currentDay = auxFunctionsHelper.getCurrentDay().lowercase(Locale.getDefault())

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
        processMangaListAiringResponse()
        processAnimeListSeasonResponse()
    }

    private fun processAnimeListTodayResponse() {
        val todayAnimeText = binding.todayAnimeText
        val todayAnimeRecyclerView = binding.todayRecyclerView
        val shimmerLayoutToday = binding.shimmerLayoutToday

        animeViewModel.getAnimeListTodayApi(currentDay)
        animeViewModel.animeListToday.observe(viewLifecycleOwner, { state ->
            when(state) {
                is ScreenStateHelper.Loading -> {
                    shimmerLayoutToday.startShimmer()
                    Log.i(TAG, "Loading Anime List Today")
                }
                is ScreenStateHelper.Success -> {
                    if(state.data != null) {
                        val animeList = state.data
                        todayAnimeRecyclerView.layoutManager =
                            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                        animeAdapter = AnimeAdapter()
                        animeAdapter.submitList(animeList)
                        animeAdapter.setHasStableIds(true)
                        todayAnimeRecyclerView.setHasFixedSize(true)
                        todayAnimeRecyclerView.setItemViewCacheSize(6)
                        todayAnimeRecyclerView.isDrawingCacheEnabled = true
                        todayAnimeRecyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
                        todayAnimeRecyclerView.adapter = animeAdapter
                        shimmerLayoutToday.stopShimmer()
                        shimmerLayoutToday.visibility = View.GONE
                        todayAnimeText.text = animeViewModel.currentDayFormatted
                        todayAnimeText.visibility = View.VISIBLE
                        todayAnimeRecyclerView.visibility = View.VISIBLE
                        Log.i(TAG, "Success Anime List Today")
                    }
                }
                is ScreenStateHelper.Error -> {
                    Log.e(TAG, "Error Anime List Today in Home Fragment With Code: ${state.message}")
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
        animeViewModel.animeListAiring.observe(viewLifecycleOwner, { state ->
            when(state) {
                is ScreenStateHelper.Loading -> {
                    shimmerLayoutAnimeAiring.startShimmer()
                    Log.i(TAG, "Loading Anime List Airing")
                }
                is ScreenStateHelper.Success -> {
                    if(state.data != null) {
                        val animeList = state.data
                        airingAnimeRecyclerView.layoutManager =
                            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                        animeAdapter = AnimeAdapter()
                        animeAdapter.submitList(animeList)
                        animeAdapter.setHasStableIds(true)
                        airingAnimeRecyclerView.setHasFixedSize(true)
                        airingAnimeRecyclerView.setItemViewCacheSize(6)
                        airingAnimeRecyclerView.isDrawingCacheEnabled = true
                        airingAnimeRecyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
                        airingAnimeRecyclerView.adapter = animeAdapter
                        shimmerLayoutAnimeAiring.stopShimmer()
                        shimmerLayoutAnimeAiring.visibility = View.GONE
                        airingAnimeText.text = getString(R.string.anime_airing)
                        airingAnimeText.visibility = View.VISIBLE
                        airingAnimeRecyclerView.visibility = View.VISIBLE
                        Log.i(TAG, "Success Anime List Airing")
                    }
                }
                is ScreenStateHelper.Error -> {
                    Log.e(TAG, "Error Anime List Airing in Home Fragment With Code: ${state.message}")
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
        mangaViewModel.mangaListAiring.observe(viewLifecycleOwner, { state ->
            when(state) {
                is ScreenStateHelper.Loading -> {
                    shimmerLayoutMangaAiring.startShimmer()
                    Log.i(TAG, "Loading Manga List Airing")
                }
                is ScreenStateHelper.Success -> {
                    val mangaList = state.data
                    mangaListAiringRecyclerView.layoutManager =
                        LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                    mangaAdapter = MangaAdapter()
                    mangaAdapter.submitList(mangaList)
                    mangaAdapter.setHasStableIds(true)
                    mangaListAiringRecyclerView.setHasFixedSize(true)
                    mangaListAiringRecyclerView.setItemViewCacheSize(6)
                    mangaListAiringRecyclerView.isDrawingCacheEnabled = true
                    mangaListAiringRecyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
                    mangaListAiringRecyclerView.adapter = mangaAdapter
                    shimmerLayoutMangaAiring.stopShimmer()
                    shimmerLayoutMangaAiring.visibility = View.GONE
                    mangaListAiringText.text = getString(R.string.manga_airing)
                    mangaListAiringText.visibility = View.VISIBLE
                    mangaListAiringRecyclerView.visibility = View.VISIBLE
                    Log.i(TAG, "Success Manga List Airing")
                }
                is ScreenStateHelper.Error -> {
                    Log.e(TAG, "Error Manga List Airing in Home Fragment With Code: ${state.message}")
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
        animeViewModel.getAnimeListSeasonApi()
        animeViewModel.animeListSeason.observe(viewLifecycleOwner, { state ->
            when(state) {
                is ScreenStateHelper.Loading -> {
                    shimmerLayoutSeason.startShimmer()
                    Log.i(TAG, "Loading Anime List Season")
                }
                is ScreenStateHelper.Success -> {
                    if(state.data != null) {
                        val animeList = state.data
                        seasonAnimeRecyclerView.layoutManager =
                            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                        animeAdapter = AnimeAdapter()
                        animeAdapter.submitList(animeList)
                        animeAdapter.setHasStableIds(true)
                        seasonAnimeRecyclerView.setHasFixedSize(true)
                        seasonAnimeRecyclerView.setItemViewCacheSize(6)
                        seasonAnimeRecyclerView.isDrawingCacheEnabled = true
                        seasonAnimeRecyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
                        seasonAnimeRecyclerView.adapter = animeAdapter
                        shimmerLayoutSeason.stopShimmer()
                        shimmerLayoutSeason.visibility = View.GONE
                        seasonAnimeText.text = animeViewModel.currentSeasonFormatted
                        seasonAnimeText.visibility = View.VISIBLE
                        seasonAnimeRecyclerView.visibility = View.VISIBLE
                        Log.i(TAG, "Success Anime List Season")
                    }
                }
                is ScreenStateHelper.Error -> {
                    Log.e(TAG, "Error Anime List Season in Home Fragment With Code: ${state.message}")
                }
                else -> {

                }
            }
        })
    }
}