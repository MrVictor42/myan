package com.victor.myan.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar
import com.victor.myan.adapter.AnimeAdapter
import com.victor.myan.databinding.FragmentHomeBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Anime
import com.victor.myan.viewmodel.SeasonViewModel
import com.victor.myan.viewmodel.TodayAnimeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var animeAdapter: AnimeAdapter
    private lateinit var shimmerFrameLayout : ShimmerFrameLayout
    private val viewModelAnimeToday by lazy { ViewModelProvider(this).get(TodayAnimeViewModel::class.java) }
    private val viewModelAnimeSeason by lazy { ViewModelProvider(this).get(SeasonViewModel::class.java) }

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
        shimmerFrameLayout = binding.shimmerFrameLayout

        viewModelAnimeToday.animeListToday.observe(viewLifecycleOwner, { state ->
            processTodayAnimeListResponse(state)
        })

        viewModelAnimeSeason.animeListSeason.observe(viewLifecycleOwner, { state ->
            processSeasonAnimeListResponse(state)
        })
    }

    @SuppressLint("InflateParams")
    private fun processTodayAnimeListResponse(state: ScreenStateHelper<List<Anime>?>?) {
        val todayAnimeText = binding.todayAnimeText
        val todayAnimeRecyclerView = binding.recyclerViewToday

        when(state) {
            is ScreenStateHelper.Loading -> {
                shimmerFrameLayout.startShimmer()
            }
            is ScreenStateHelper.Success -> {
                if(state.data != null) {
                    val animeList = state.data
                    todayAnimeRecyclerView.layoutManager =
                        LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                    animeAdapter = AnimeAdapter()
                    animeAdapter.submitList(animeList)
                    todayAnimeRecyclerView.adapter = animeAdapter
//                    shimmerFrameLayout.stopShimmer()
//                    shimmerFrameLayout.visibility = View.GONE
//                    todayAnimeText.visibility = View.VISIBLE
                    todayAnimeText.text = viewModelAnimeToday.currentDayFormatted
                }
            }
            is ScreenStateHelper.Error -> {
                val view = binding.nestedScrollView
                shimmerFrameLayout.stopShimmer()
                Snackbar.make(view, "Connection with internet not found...", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun processSeasonAnimeListResponse(state: ScreenStateHelper<List<Anime>?>?) {
        val seasonAnimeText = binding.seasonAnimeText
        val seasonAnimeRecyclerView = binding.recyclerViewSeason

        when(state) {
            is ScreenStateHelper.Loading -> {
                shimmerFrameLayout.startShimmer()
            }
            is ScreenStateHelper.Success -> {
                if(state.data != null) {
                    val animeList = state.data
                    seasonAnimeRecyclerView.layoutManager =
                        LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                    animeAdapter = AnimeAdapter()
                    animeAdapter.submitList(animeList)
                    seasonAnimeRecyclerView.adapter = animeAdapter
                    shimmerFrameLayout.stopShimmer()
                    shimmerFrameLayout.visibility = View.GONE
                    seasonAnimeText.visibility = View.VISIBLE
                    seasonAnimeText.text = viewModelAnimeSeason.currentSeasonFormatted
                }
            }
            is ScreenStateHelper.Error -> {
                val view = binding.nestedScrollView
                shimmerFrameLayout.stopShimmer()
                Snackbar.make(view, "Connection with internet not found...", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}