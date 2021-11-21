package com.victor.myan.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.victor.myan.adapter.RecyclerViewVerticalAdapter
import com.victor.myan.databinding.FragmentHomeBinding
import com.victor.myan.helper.AuxFunctionsHelper
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Categories
import com.victor.myan.viewmodel.AnimeViewModel
import com.victor.myan.viewmodel.MangaViewModel
import java.util.Locale

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerViewVerticalAdapter: RecyclerViewVerticalAdapter
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
    private val categoryList : MutableList<Categories> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerViewAnime = binding.recyclerViewAnime

        frameLoading = binding.frameLoading
        frameHome = binding.frameContent
        feed()
    }

    private fun feed() {
        animeViewModel.getAnimeListTodayApi(currentDay)
        animeViewModel.animeListToday.observe(viewLifecycleOwner, { animeToday ->
            when(animeToday) {
                is ScreenStateHelper.Loading -> {

                }
                is ScreenStateHelper.Success -> {
                    if(animeToday.data != null) {
                        val category = Categories()

                        category.type = "anime"
                        category.title = animeViewModel.currentDayFormatted
                        category.categories.addAll(animeToday.data)

                        categoryList.add(category)
                        processAnimeListAiringResponse()
                    }
                }
                is ScreenStateHelper.Error -> {

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
                        val category = Categories()

                        category.type = "anime"
                        category.title = "Anime Airing"
                        category.categories.addAll(airingAnime.data)

                        categoryList.add(category)
                        processMangaListAiringResponse()
                    }
                }
                is ScreenStateHelper.Error -> {

                }
                else -> {

                }
            }
        })
    }

    private fun processMangaListAiringResponse() {
        mangaViewModel.getMangaListAiringApi()
        mangaViewModel.mangaListAiring.observe(viewLifecycleOwner, { mangaAiring ->
            when(mangaAiring) {
                is ScreenStateHelper.Loading -> {

                }
                is ScreenStateHelper.Success -> {
                    if(mangaAiring.data != null) {
                        val category = Categories()

                        category.type = "manga"
                        category.title = "Manga Airing"
                        category.categories.addAll(mangaAiring.data)

                        categoryList.add(category)
                        processAnimeListSeasonResponse()
                        activity?.runOnUiThread {
                            recyclerViewVerticalAdapter = RecyclerViewVerticalAdapter(categoryList)
                            recyclerViewAnime.adapter = recyclerViewVerticalAdapter
                        }
                    }
                }
                is ScreenStateHelper.Error -> {

                }
                else -> {

                }
            }
        })
    }

    private fun processAnimeListSeasonResponse() {
        animeViewModel.getAnimeListSeasonApi(currentYear, currentSeason)
        animeViewModel.animeListSeason.observe(viewLifecycleOwner, { seasonAnime ->
            when(seasonAnime) {
                is ScreenStateHelper.Loading -> {

                }
                is ScreenStateHelper.Success -> {
                    if(seasonAnime.data != null) {
                        val category = Categories()

                        category.type = "anime"
                        category.title = animeViewModel.currentSeasonFormatted
                        category.categories.addAll(seasonAnime.data)

                        categoryList.add(category)
                        activity?.runOnUiThread {
                            recyclerViewVerticalAdapter = RecyclerViewVerticalAdapter(categoryList)
                            recyclerViewAnime.adapter = recyclerViewVerticalAdapter
                        }
                        frameLoading.visibility = View.GONE
                        frameHome.visibility = View.VISIBLE
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