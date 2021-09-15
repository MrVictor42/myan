package com.victor.myan.fragments.tablayouts.genreDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.adapter.AnimeAdapter
import com.victor.myan.adapter.MangaAdapter
import com.victor.myan.databinding.FragmentAiringBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Anime
import com.victor.myan.model.Manga
import com.victor.myan.viewmodel.GenreViewModel

class AiringFragment : Fragment() {

    private lateinit var binding : FragmentAiringBinding
    private lateinit var animeAdapter: AnimeAdapter
    private lateinit var mangaAdapter: MangaAdapter
    private val genreViewModel by lazy {
        ViewModelProvider(this).get(GenreViewModel::class.java)
    }

    companion object {
        fun newInstance(mal_id : Int, type : String): AiringFragment {
            val airingFragment = AiringFragment()
            val args = Bundle()
            args.putInt("mal_id", mal_id)
            args.putString("type", type)
            airingFragment.arguments = args
            return airingFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAiringBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val selected = arguments?.getString("type")
        val genreID = arguments?.getInt("mal_id")!!

        when(selected) {
            "anime" -> {
                genreViewModel.resultAiringApi("anime", genreID, "airing")
                genreViewModel.resultAnimeList.observe(viewLifecycleOwner, { state ->
                    processAnimeAiringResponse(state, genreID)
                })
            }
            "manga" -> {
                genreViewModel.resultAiringApi("manga", genreID, "airing")
                genreViewModel.resultMangaList.observe(viewLifecycleOwner, { state ->
                    processMangaAiringResponse(state, genreID)
                })
            }
        }
    }

    private fun processMangaAiringResponse(state: ScreenStateHelper<List<Manga>?>?, genreID: Int) {
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
                    mangaAdapter = MangaAdapter()
                    mangaAdapter.submitList(mangaList)
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
                genreViewModel.resultAiringApi("manga", genreID, "airing")
            }
        }
    }

    private fun processAnimeAiringResponse(state: ScreenStateHelper<List<Anime>?>?, genreID : Int) {
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
                    animeAdapter = AnimeAdapter()
                    animeAdapter.submitList(animeList)
                    airingRecyclerView.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                    airingRecyclerView.adapter = animeAdapter
                    airingRecyclerView.visibility = View.VISIBLE
                }
            }
            is ScreenStateHelper.Empty -> {
                emptyText.text = state.message
                emptyText.visibility = View.VISIBLE
                airingRecyclerView.visibility = View.GONE
            }
            is ScreenStateHelper.Error -> {
                genreViewModel.resultAiringApi("anime", genreID, "airing")
            }
        }
    }
}