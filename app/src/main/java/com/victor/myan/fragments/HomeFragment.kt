package com.victor.myan.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.victor.myan.R
import com.victor.myan.adapter.AnimeAdapter
import com.victor.myan.adapter.MangaAdapter
import com.victor.myan.databinding.FragmentHomeBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.baseFragments.BaseAnimeDetailFragment
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
        processAnimeListCarouselResponse()
        processAnimeListTodayResponse()
        processAnimeListSeasonResponse()
        SystemClock.sleep(2000)
        processAnimeListTopResponse()
        processMangaListTopResponse()
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
                    SystemClock.sleep(2000)
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
                    SystemClock.sleep(2000)
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
                    SystemClock.sleep(2000)
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
                    processAnimeListTodayResponse()
                    SystemClock.sleep(2000)
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
                    SystemClock.sleep(2000)
                }
                else -> {

                }
            }
        })
    }

    @SuppressLint("InflateParams")
    private fun processAnimeListCarouselResponse() {
        val carouselView = binding.carouselView.carouselViewCarousel

        animeViewModel.getAnimeListAiringCarouselApi(12)
        animeViewModel.animeListCarousel.observe(viewLifecycleOwner, { state ->
            when(state) {
                null -> {

                }
                is ScreenStateHelper.Loading -> {

                }
                is ScreenStateHelper.Success -> {
                    if(state.data != null) {
                        val animeList = state.data
                        for(aux in animeList.indices) {
                            carouselView.setViewListener { position ->
                                val viewCarousel = layoutInflater.inflate(R.layout.fragment_carousel_anime_list, null)
                                val animeTitle = viewCarousel.findViewById<TextView>(R.id.anime_title_carousel)
                                val animeImage = viewCarousel.findViewById<ImageView>(R.id.anime_image_carousel)

                                Glide.with(viewCarousel.context)
                                    .load(animeList[position].image_url)
                                    .placeholder(R.drawable.ic_launcher_foreground)
                                    .error(R.drawable.ic_launcher_foreground)
                                    .fallback(R.drawable.ic_launcher_foreground)
                                    .fitCenter()
                                    .into(animeImage)
                                animeTitle.text = animeList[position].title

                                viewCarousel
                            }

                            carouselView.setImageClickListener { position ->
                                val fragment = BaseAnimeDetailFragment()
                                val fragmentManager = activity?.supportFragmentManager

                                val bundle = Bundle()
                                bundle.putString("mal_id", animeList[position].mal_id)

                                fragment.arguments = bundle

                                val transaction = fragmentManager?.beginTransaction()?.replace(R.id.fragment_layout, fragment)
                                transaction?.commit()
                                fragmentManager?.beginTransaction()?.commit()
                            }
                        }
                        carouselView.pageCount = animeList.size
                    }
                }
                is ScreenStateHelper.Error -> {
                    animeViewModel.getAnimeListAiringCarouselApi(12)
                    SystemClock.sleep(2000)
                }
                else -> {

                }
            }
        })
    }
}