package com.victor.myan.fragments.tablayouts.anime

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.victor.myan.adapter.EpisodesAdapter
import com.victor.myan.databinding.FragmentEpisodesAnimeBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.viewmodel.AnimeViewModel

class EpisodesAnimeFragment(
    private val malID: Int
) : Fragment() {

    private lateinit var binding : FragmentEpisodesAnimeBinding
    private lateinit var episodesAdapter: EpisodesAdapter
    private val animeViewModel by lazy {
        ViewModelProvider(this)[AnimeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodesAnimeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val shimmerLayout = binding.shimmerLayout
        val emptyList = binding.emptyList
        val recyclerView = binding.recyclerView

        animeViewModel.getAnimeEpisode(malID)
        animeViewModel.animeListEpisode.observe(viewLifecycleOwner, { episodes ->
            when(episodes) {
                is ScreenStateHelper.Loading -> {
                    shimmerLayout.startShimmer()
                }
                is ScreenStateHelper.Success -> {
                    if(episodes.data != null) {
                        val episodeList = episodes.data
                        episodesAdapter = EpisodesAdapter(episodeList)
                        recyclerView.adapter = episodesAdapter
                        shimmerLayout.stopShimmer()
                        shimmerLayout.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                    }
                }
                is ScreenStateHelper.Error -> {
                    emptyList.text = episodes.message
                    emptyList.visibility = View.VISIBLE
                    shimmerLayout.stopShimmer()
                    shimmerLayout.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                }
                is ScreenStateHelper.Empty -> {
                    emptyList.text = episodes.message
                    emptyList.visibility = View.VISIBLE
                    shimmerLayout.stopShimmer()
                    shimmerLayout.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                }
            }
        })
    }
}