package com.victor.myan.fragments.tablayouts.genre

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.adapter.AnimeAdapter
import com.victor.myan.adapter.MangaAdapter
import com.victor.myan.databinding.FragmentOptionsGenresBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.viewmodel.GenreViewModel

class OptionsGenres(
    private val malID: Int, private val selected: String, private val mode: String
) : Fragment() {

    private lateinit var binding : FragmentOptionsGenresBinding
    private lateinit var animeAdapter: AnimeAdapter
    private lateinit var mangaAdapter: MangaAdapter
    private val genreViewModel by lazy {
        ViewModelProvider(this)[GenreViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOptionsGenresBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val airingRecyclerView = binding.recyclerView
        val shimmerLayout = binding.shimmerLayout
        val message = binding.message
        val imgRefresh = binding.imgRefresh
        val retryAgainText = binding.textTryAgain
        val btnRefresh = binding.btnRefresh

        when(selected) {
            "anime" -> {
                genreViewModel.resultSearchApi("anime", malID, mode)
                genreViewModel.resultAnimeList.observe(viewLifecycleOwner, { animeList ->
                    when(animeList) {
                        is ScreenStateHelper.Loading -> {

                        }
                        is ScreenStateHelper.Success -> {
                            if(animeList.data != null) {
                                val animeGenreList = animeList.data
                                airingRecyclerView.layoutManager =
                                    GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                                animeAdapter = AnimeAdapter()
                                animeAdapter.setData(animeGenreList)
                                airingRecyclerView.adapter = animeAdapter
                                shimmerLayout.visibility = View.GONE
                                airingRecyclerView.visibility = View.VISIBLE
                            }
                        }
                        is ScreenStateHelper.Error -> {
                            imgRefresh.visibility = View.VISIBLE
                            retryAgainText.visibility = View.VISIBLE
                            btnRefresh.visibility = View.VISIBLE
                            message.text = animeList.message
                            message.visibility = View.VISIBLE
                            shimmerLayout.visibility = View.GONE
                            airingRecyclerView.visibility = View.GONE

                            btnRefresh.setOnClickListener {
                                onViewCreated(view, savedInstanceState)

                                imgRefresh.visibility = View.GONE
                                retryAgainText.visibility = View.GONE
                                btnRefresh.visibility = View.GONE
                                message.visibility = View.GONE
                            }
                        }
                        is ScreenStateHelper.Empty -> {
                            message.text = animeList.message
                            message.visibility = View.VISIBLE
                            shimmerLayout.visibility = View.GONE
                            airingRecyclerView.visibility = View.GONE
                        }
                        else -> {

                        }
                    }
                })
            }
            "manga" -> {
                val typeManga = if(mode == "upcoming") {
                    "to_be_published"
                } else {
                    mode
                }
                genreViewModel.resultSearchApi("manga", malID, typeManga)
                genreViewModel.resultMangaList.observe(viewLifecycleOwner, { mangaList ->
                    when(mangaList) {
                        is ScreenStateHelper.Loading -> {

                        }
                        is ScreenStateHelper.Success -> {
                            if(mangaList.data != null) {
                                val mangaGenreList = mangaList.data
                                airingRecyclerView.layoutManager =
                                    GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                                mangaAdapter = MangaAdapter()
                                mangaAdapter.setData(mangaGenreList)
                                airingRecyclerView.adapter = mangaAdapter
                                shimmerLayout.visibility = View.GONE
                                airingRecyclerView.visibility = View.VISIBLE
                            }
                        }
                        is ScreenStateHelper.Error -> {
                            imgRefresh.visibility = View.VISIBLE
                            retryAgainText.visibility = View.VISIBLE
                            btnRefresh.visibility = View.VISIBLE
                            message.text = mangaList.message
                            message.visibility = View.VISIBLE
                            shimmerLayout.visibility = View.GONE
                            airingRecyclerView.visibility = View.GONE

                            btnRefresh.setOnClickListener {
                                onViewCreated(view, savedInstanceState)

                                imgRefresh.visibility = View.GONE
                                retryAgainText.visibility = View.GONE
                                btnRefresh.visibility = View.GONE
                                message.visibility = View.GONE
                            }
                        }
                        is ScreenStateHelper.Empty -> {
                            message.text = mangaList.message
                            message.visibility = View.VISIBLE
                            shimmerLayout.visibility = View.GONE
                            airingRecyclerView.visibility = View.GONE
                        }
                        else -> {

                        }
                    }
                })
            }
        }
    }
}