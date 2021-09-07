package com.victor.myan.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victor.myan.R
import com.victor.myan.adapter.AnimeAdapter
import com.victor.myan.adapter.MangaAdapter
import com.victor.myan.databinding.FragmentHomeBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.viewmodel.AnimeViewModel
import com.victor.myan.viewmodel.MangaViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var animeAdapter: AnimeAdapter
    private lateinit var mangaAdapter: MangaAdapter
    private val animeViewModel by lazy {
        ViewModelProvider(this).get(AnimeViewModel::class.java)
    }
    private val mangaViewModel by lazy {
        ViewModelProvider(this).get(MangaViewModel::class.java)
    }

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
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        processAnimeListTodayResponse()
        processAnimeListAiringResponse()
        processMangaListAiringResponse()
    }

    override fun onResume() {
        processAnimeListSeasonResponse()
        processAnimeListTopResponse()
        processMangaListTopResponse()
        super.onResume()
    }

    private fun processMangaListAiringResponse() {
        val mangaListAiringText = binding.mangaAiring.titleRecyclerView
        val mangaListAiringRecyclerView = binding.mangaAiring.recyclerView

        mangaViewModel.getMangaListAiringApi()
        mangaViewModel.mangaListAiring.observe(viewLifecycleOwner, { state ->
            when(state) {
                null -> {

                }
                is ScreenStateHelper.Loading -> {

                }
                is ScreenStateHelper.Success -> {
                    val mangaList = state.data
                    mangaListAiringRecyclerView.layoutManager =
                        LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                    mangaAdapter = MangaAdapter()
                    mangaAdapter.submitList(mangaList)
                    mangaListAiringRecyclerView.adapter = mangaAdapter
                    mangaListAiringText.text = getString(R.string.manga_airing)
                    mangaListAiringText.visibility = View.VISIBLE
                    mangaListAiringRecyclerView.visibility = View.VISIBLE
                }
                is ScreenStateHelper.Error -> {
                    mangaViewModel.getMangaListAiringApi()
                }
                else -> {

                }
            }
        })
    }

    private fun processMangaListTopResponse() {
        val topMangaText = binding.topManga.titleRecyclerView
        val topMangaRecyclerView = binding.topManga.recyclerView

        mangaViewModel.getMangaListTopApi()
        mangaViewModel.mangaTopList.observe(viewLifecycleOwner, { state ->
            when(state) {
                null -> {

                }
                is ScreenStateHelper.Loading -> {

                }
                is ScreenStateHelper.Success -> {
                    val mangaList = state.data
                    topMangaRecyclerView.layoutManager =
                        LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                    mangaAdapter = MangaAdapter()
                    mangaAdapter.submitList(mangaList)
                    topMangaRecyclerView.adapter = mangaAdapter
                    topMangaText.text = getString(R.string.top_manga)
                    topMangaText.visibility = View.VISIBLE
                    topMangaRecyclerView.visibility = View.VISIBLE
                }
                is ScreenStateHelper.Error -> {
                    mangaViewModel.getMangaListTopApi()
                }
                else -> {

                }
            }
        })
    }

    private fun processAnimeListTopResponse() {
        val topAnimeText = binding.topAnime.titleRecyclerView
        val topAnimeRecyclerView = binding.topAnime.recyclerView

        animeViewModel.getAnimeListTopApi()
        animeViewModel.animeListTop.observe(viewLifecycleOwner, { state ->
            when(state) {
                null -> {

                }
                is ScreenStateHelper.Loading -> {

                }
                is ScreenStateHelper.Success -> {
                    val animeList = state.data
                    topAnimeRecyclerView.layoutManager =
                        LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                    animeAdapter = AnimeAdapter()
                    animeAdapter.submitList(animeList)
                    topAnimeRecyclerView.adapter = animeAdapter
                    topAnimeText.text = getString(R.string.top_anime)
                    topAnimeText.visibility = View.VISIBLE
                    topAnimeRecyclerView.visibility = View.VISIBLE
                }
                is ScreenStateHelper.Error -> {
                    animeViewModel.getAnimeListTopApi()
                }
                else -> {

                }
            }
        })
    }

    private fun processAnimeListSeasonResponse() {
        val seasonAnimeText = binding.seasonAnime.titleRecyclerView
        val seasonAnimeRecyclerView = binding.seasonAnime.recyclerView

        animeViewModel.getAnimeListSeasonApi()
        animeViewModel.animeListSeason.observe(viewLifecycleOwner, { state ->
            when(state) {
                null -> {

                }
                is ScreenStateHelper.Loading -> {

                }
                is ScreenStateHelper.Success -> {
                    if(state.data != null) {
                        val animeList = state.data
                        seasonAnimeRecyclerView.layoutManager =
                            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                        animeAdapter = AnimeAdapter()
                        animeAdapter.submitList(animeList)
                        seasonAnimeRecyclerView.adapter = animeAdapter
                        seasonAnimeText.text = animeViewModel.currentSeasonFormatted
                        seasonAnimeText.visibility = View.VISIBLE
                        seasonAnimeRecyclerView.visibility = View.VISIBLE
                    }
                }
                is ScreenStateHelper.Error -> {
                    animeViewModel.getAnimeListSeasonApi()
                }
                else -> {

                }
            }
        })
    }

    private fun processAnimeListTodayResponse() {
        val todayAnimeText = binding.todayAnime.titleRecyclerView
        val todayAnimeRecyclerView = binding.todayAnime.recyclerView

        animeViewModel.getAnimeListTodayApi()
        animeViewModel.animeListToday.observe(viewLifecycleOwner, { state ->
            when(state) {
                null -> {

                }
                is ScreenStateHelper.Loading -> {

                }
                is ScreenStateHelper.Success -> {
                    if(state.data != null) {
                        val animeList = state.data
                        todayAnimeRecyclerView.layoutManager =
                            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                        animeAdapter = AnimeAdapter()
                        animeAdapter.submitList(animeList)
                        todayAnimeRecyclerView.adapter = animeAdapter
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
        val airingAnimeText = binding.animeAiring.titleRecyclerView
        val airingAnimeRecyclerView = binding.animeAiring.recyclerView

        animeViewModel.getAnimeListAiringApi()
        animeViewModel.animeListAiring.observe(viewLifecycleOwner, { state ->
            when(state) {
                null -> {

                }
                is ScreenStateHelper.Loading -> {

                }
                is ScreenStateHelper.Success -> {
                    if(state.data != null) {
                        val animeList = state.data
                        airingAnimeRecyclerView.layoutManager =
                            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                        animeAdapter = AnimeAdapter()
                        animeAdapter.submitList(animeList)
                        airingAnimeRecyclerView.adapter = animeAdapter
                        airingAnimeText.text = getString(R.string.anime_airing)
                        airingAnimeText.visibility = View.VISIBLE
                        airingAnimeRecyclerView.visibility = View.VISIBLE
                    }
                }
                is ScreenStateHelper.Error -> {
                    animeViewModel.getAnimeListAiringApi()
                }
                else -> {

                }
            }
        })
    }
}