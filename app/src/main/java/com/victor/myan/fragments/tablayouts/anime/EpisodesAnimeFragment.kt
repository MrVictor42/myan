package com.victor.myan.fragments.tablayouts.anime

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.victor.myan.adapter.VideosAdapter
import com.victor.myan.databinding.FragmentEpisodesAnimeBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.viewmodel.AnimeViewModel

class EpisodesAnimeFragment (
    private val malID: Int
) : Fragment() {

    private lateinit var binding : FragmentEpisodesAnimeBinding
    private lateinit var videosAdapter: VideosAdapter
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
        val errorOptions = binding.errorOptions.errorOptions
        val btnRefresh = binding.errorOptions.btnRefresh

        animeViewModel.getAnimeEpisode(malID)
        animeViewModel.animeListEpisode.observe(viewLifecycleOwner, { episodes ->
            when(episodes) {
                is ScreenStateHelper.Loading -> {
                    shimmerLayout.startShimmer()
                }
                is ScreenStateHelper.Success -> {
                    if(episodes.data != null) {
                        val episodeList = episodes.data
                        videosAdapter = VideosAdapter(episodeList)
                        recyclerView.adapter = videosAdapter
                        shimmerLayout.stopShimmer()
                        shimmerLayout.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                    }
                }
                is ScreenStateHelper.Error -> {
                    errorOptions.visibility = View.VISIBLE
                    shimmerLayout.visibility = View.GONE

                    btnRefresh.setOnClickListener {
                        onViewCreated(view, savedInstanceState)

                        errorOptions.visibility = View.GONE
                    }
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