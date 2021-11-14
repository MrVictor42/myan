package com.victor.myan.fragments.tablayouts.genre

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.adapter.AnimeHorizontalAdapter
import com.victor.myan.adapter.MangaAdapter
import com.victor.myan.databinding.FragmentUpcomingBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Anime
import com.victor.myan.model.Manga
import com.victor.myan.viewmodel.GenreViewModel

class UpcomingFragment : Fragment() {

    private lateinit var binding : FragmentUpcomingBinding
    private lateinit var animeHorizontalAdapter: AnimeHorizontalAdapter
    private lateinit var mangaAdapter: MangaAdapter
    private val genreViewModel by lazy {
        ViewModelProvider(this).get(GenreViewModel::class.java)
    }

    companion object {
        fun newInstance(mal_id : Int, type : String): UpcomingFragment {
            val upcomingFragment = UpcomingFragment()
            val args = Bundle()
            args.putInt("mal_id", mal_id)
            args.putString("type", type)
            upcomingFragment.arguments = args
            return upcomingFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpcomingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val selected = arguments?.getString("type")
        val genreID = arguments?.getInt("mal_id")!!

        when(selected) {
            "anime" -> {
                genreViewModel.resultAiringApi("anime", genreID, "upcoming")
                genreViewModel.resultAnimeList.observe(viewLifecycleOwner, { state ->
                    processAnimeResponse(state, genreID)
                })
            }
            "manga" -> {
                genreViewModel.resultAiringApi("manga", genreID, "upcoming")
                genreViewModel.resultMangaList.observe(viewLifecycleOwner, { state ->
                    processMangaResponse(state, genreID)
                })
            }
        }
    }

    private fun processMangaResponse(state: ScreenStateHelper<List<Manga>?>?, genreID: Int) {
        val emptyText = binding.emptyListTextView
        val airingRecyclerView = binding.recyclerView.recyclerViewVertical

        when(state) {
            is ScreenStateHelper.Loading -> {

            }
            is ScreenStateHelper.Success -> {
                if(state.data != null) {
                    val mangaList = state.data
                    airingRecyclerView.setHasFixedSize(true)
                    airingRecyclerView.setItemViewCacheSize(10)
                    mangaAdapter.setData(mangaList)
                    airingRecyclerView.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                    airingRecyclerView.adapter = mangaAdapter
                    airingRecyclerView.visibility = View.VISIBLE
                }
            }
            is ScreenStateHelper.Empty -> {
                emptyText.text = state.message
                emptyText.visibility = View.VISIBLE
                airingRecyclerView.visibility = View.GONE
            }
            is ScreenStateHelper.Error -> {
                genreViewModel.resultAiringApi("manga", genreID, "upcoming")
            }
        }
    }

    private fun processAnimeResponse(state: ScreenStateHelper<List<Anime>?>?, genreID : Int) {
        val emptyText = binding.emptyListTextView
        val airingRecyclerView = binding.recyclerView.recyclerViewVertical

        when(state) {
            is ScreenStateHelper.Loading -> {

            }
            is ScreenStateHelper.Success -> {
                if(state.data != null) {
                    val animeList = state.data
                    airingRecyclerView.setHasFixedSize(true)
                    airingRecyclerView.setItemViewCacheSize(10)
                    animeHorizontalAdapter = AnimeHorizontalAdapter()
//                    animeAdapter.submitList(animeList)
                    airingRecyclerView.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                    airingRecyclerView.adapter = animeHorizontalAdapter
                    airingRecyclerView.visibility = View.VISIBLE
                }
            }
            is ScreenStateHelper.Empty -> {
                emptyText.text = state.message
                emptyText.visibility = View.VISIBLE
                airingRecyclerView.visibility = View.GONE
            }
            is ScreenStateHelper.Error -> {
                genreViewModel.resultAiringApi("anime", genreID, "upcoming")
            }
        }
    }
}