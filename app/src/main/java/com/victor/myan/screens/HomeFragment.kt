package com.victor.myan.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.victor.myan.R
import com.victor.myan.adapter.AnimeAdapter
import com.victor.myan.adapter.MangaAdapter
import com.victor.myan.databinding.FragmentHomeBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Anime
import com.victor.myan.model.Manga
import com.victor.myan.screens.animeDetail.BaseAnimeDetailFragment
import com.victor.myan.viewmodel.AnimeListCarouselViewModel
import com.victor.myan.viewmodel.SeasonViewModel
import com.victor.myan.viewmodel.TodayAnimeViewModel
import com.victor.myan.viewmodel.TopAnimeViewModel
import com.victor.myan.viewmodel.TopMangaViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var animeAdapter: AnimeAdapter
    private lateinit var mangaAdapter : MangaAdapter
    private val viewModelAnimeCarousel by lazy { ViewModelProvider(this).get(
        AnimeListCarouselViewModel::class.java) }
    private val viewModelAnimeToday by lazy { ViewModelProvider(this).get(TodayAnimeViewModel::class.java) }
    private val viewModelAnimeSeason by lazy { ViewModelProvider(this).get(SeasonViewModel::class.java) }
    private val viewModelAnimeTop by lazy { ViewModelProvider(this).get(TopAnimeViewModel::class.java) }
    private val viewModelMangaTop by lazy { ViewModelProvider(this).get(TopMangaViewModel::class.java) }

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

        viewModelAnimeCarousel.animeListCarouselLiveData.observe(viewLifecycleOwner, { state ->
            processAnimeListCarouselResponse(state)
        })

        viewModelAnimeToday.animeListToday.observe(viewLifecycleOwner, { state ->
            processTodayAnimeListResponse(state)
        })

        viewModelAnimeSeason.animeListSeason.observe(viewLifecycleOwner, { state ->
            processSeasonAnimeListResponse(state)
        })

        Handler(Looper.getMainLooper()).postDelayed({
            viewModelAnimeTop.animeListTopLiveData.observe(viewLifecycleOwner, { state ->
                processTopAnimeListResponse(state)
            })
        }, 2000)

        Handler(Looper.getMainLooper()).postDelayed({
            viewModelMangaTop.mangaListTopLiveData.observe(viewLifecycleOwner, { state ->
                processTopMangaListResponse(state)
            })
        }, 2000)
    }

    @SuppressLint("InflateParams")
    private fun processAnimeListCarouselResponse(state: ScreenStateHelper<List<Anime>?>?) {
        val progressBar = binding.progressBarCarousel
        val carouselView = binding.carouselViewCarousel

        when(state) {
            is ScreenStateHelper.Loading -> {
                progressBar.visibility = View.VISIBLE
            }
            is ScreenStateHelper.Success -> {
                if(state.data != null) {
                    val animeList = state.data
                    for(aux in animeList.indices) {
                        carouselView.setViewListener { position ->
                            val viewCarousel = layoutInflater.inflate(R.layout.fragment_carousel_anime_list, null)
                            val animeTitle = viewCarousel.findViewById<TextView>(R.id.anime_title_carousel)
                            val animeImage = viewCarousel.findViewById<ImageView>(R.id.anime_image_carousel)

                            Glide.with(viewCarousel.context).load(animeList[position].image_url).into(animeImage)
                            animeTitle.text = animeList[position].title

                            viewCarousel
                        }

                        carouselView.setImageClickListener { position ->
                            val fragment = BaseAnimeDetailFragment()
                            val fragmentManager = activity?.supportFragmentManager

                            val bundle = Bundle()
                            bundle.putString("mal_id", animeList[position].mal_id)

                            fragment.arguments = bundle

                            val transaction = fragmentManager?.beginTransaction()?.replace(R.id.content, fragment)
                            transaction?.commit()
                            fragmentManager?.beginTransaction()?.commit()
                        }
                    }
                    progressBar.visibility = View.GONE
                    carouselView.pageCount = animeList.size
                }
            }
            is ScreenStateHelper.Error -> {
                progressBar.visibility = View.VISIBLE
                val view = progressBar.rootView
                Snackbar.make(view,
                    "Connection with internet not found or internal error... Try again later",
                    Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun processTodayAnimeListResponse(state: ScreenStateHelper<List<Anime>?>?) {
        val todayAnimeText = binding.todayAnimeText
        val todayAnimeRecyclerView = binding.recyclerViewToday

        when(state) {
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
                    todayAnimeText.text = viewModelAnimeToday.currentDayFormatted
                    todayAnimeText.visibility = View.VISIBLE
                }
            }
            is ScreenStateHelper.Error -> {
                val view = binding.nestedScrollView
                Snackbar.make(view, "Connection with internet not found...", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun processSeasonAnimeListResponse(state: ScreenStateHelper<List<Anime>?>?) {
        val seasonAnimeText = binding.seasonAnimeText
        val seasonAnimeRecyclerView = binding.recyclerViewSeason

        when(state) {
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
                    seasonAnimeText.text = viewModelAnimeSeason.currentSeasonFormatted
                    seasonAnimeText.visibility = View.VISIBLE
                }
            }
            is ScreenStateHelper.Error -> {
                val view = binding.nestedScrollView

                Snackbar.make(view, "Connection with internet not found...", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun processTopAnimeListResponse(state: ScreenStateHelper<List<Anime>?>?) {
        val topAnimeText = binding.topAnimeText
        val topAnimeRecyclerView = binding.recyclerViewTopAnime

        when(state) {
            is ScreenStateHelper.Loading -> {

            }
            is ScreenStateHelper.Success -> {
                val animeList = state.data
                topAnimeRecyclerView.layoutManager =
                    LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                animeAdapter = AnimeAdapter()
                animeAdapter.submitList(animeList)
                topAnimeRecyclerView.adapter = animeAdapter
                topAnimeText.visibility = View.VISIBLE
            }
            is ScreenStateHelper.Error -> {
                val view = binding.nestedScrollView

                Snackbar.make(view, "Connection with internet not found...", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun processTopMangaListResponse(state: ScreenStateHelper<List<Manga>?>?) {
        val topMangaText = binding.topMangaText
        val topMangaRecyclerView = binding.recyclerViewTopManga

        when(state) {
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
            }
            is ScreenStateHelper.Error -> {
                val view = binding.nestedScrollView
                Snackbar.make(view, "Connection with internet not found...", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}