package com.victor.myan.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.adapter.AnimeAdapter
import com.victor.myan.adapter.MangaAdapter
import com.victor.myan.databinding.FragmentAnimeMangaBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.viewmodel.ActorViewModel
import com.victor.myan.viewmodel.CharacterViewModel
import com.victor.myan.viewmodel.RecommendationViewModel

class AnimeMangaFragment(
    private val malID : Int?, private val type : String?
) : Fragment() {

    private lateinit var binding : FragmentAnimeMangaBinding
    private lateinit var animeAdapter : AnimeAdapter
    private lateinit var mangaAdapter : MangaAdapter
    private val characterViewModel by lazy {
        ViewModelProvider(this)[CharacterViewModel::class.java]
    }
    private val recommendationViewModel by lazy {
        ViewModelProvider(this)[RecommendationViewModel::class.java]
    }
    private val actorViewModel by lazy {
        ViewModelProvider(this)[ActorViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimeMangaBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val emptyText = binding.emptyListText
        val recyclerView = binding.recyclerView
        val shimmerLayout = binding.shimmerLayout

        if(malID != null && type != null) {
            when(type) {
                "characterAnime" -> {
                    characterViewModel.getCharacterAnimeMangaApi(malID, "anime")
                    characterViewModel.characterAnimeList.observe(viewLifecycleOwner, { animeList ->
                        when(animeList) {
                            is ScreenStateHelper.Loading -> {

                            }
                            is ScreenStateHelper.Success -> {
                                if(animeList.data != null) {
                                    val characterAnimeList = animeList.data
                                    recyclerView.layoutManager =
                                        GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                                    animeAdapter = AnimeAdapter()
                                    animeAdapter.setData(characterAnimeList)
                                    recyclerView.adapter = animeAdapter
                                    shimmerLayout.visibility = View.GONE
                                    recyclerView.visibility = View.VISIBLE
                                }
                            }
                            is ScreenStateHelper.Empty -> {
                                emptyText.text = animeList.message
                                emptyText.visibility = View.VISIBLE
                                recyclerView.visibility = View.GONE
                            }
                            is ScreenStateHelper.Error -> {
                                emptyText.text = animeList.message
                                emptyText.visibility = View.VISIBLE
                                recyclerView.visibility = View.GONE
                            }
                        }
                    })
                }
                "characterManga" -> {
                    characterViewModel.getCharacterAnimeMangaApi(malID, "manga")
                    characterViewModel.characterMangaList.observe(viewLifecycleOwner, { mangaList ->
                        when(mangaList) {
                            is ScreenStateHelper.Loading -> {

                            }
                            is ScreenStateHelper.Success -> {
                                if(mangaList.data != null) {
                                    val characterMangaList = mangaList.data
                                    recyclerView.layoutManager =
                                        GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                                    mangaAdapter = MangaAdapter()
                                    mangaAdapter.setData(characterMangaList)
                                    recyclerView.adapter = mangaAdapter
                                    shimmerLayout.visibility = View.GONE
                                    recyclerView.visibility = View.VISIBLE
                                }
                            }
                            is ScreenStateHelper.Empty -> {
                                emptyText.text = mangaList.message
                                emptyText.visibility = View.VISIBLE
                                recyclerView.visibility = View.GONE
                            }
                            is ScreenStateHelper.Error -> {
                                emptyText.text = mangaList.message
                                emptyText.visibility = View.VISIBLE
                                recyclerView.visibility = View.GONE
                            }
                        }
                    })
                }
                "recommendationAnime" -> {
                    recommendationViewModel.getRecommendationApi("anime", malID)
                    recommendationViewModel.animeList.observe(viewLifecycleOwner, { animeList ->
                        when(animeList) {
                            is ScreenStateHelper.Loading -> {

                            }
                            is ScreenStateHelper.Success -> {
                                if(animeList.data != null) {
                                    val recommendationList = animeList.data
                                    recyclerView.layoutManager =
                                        GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                                    animeAdapter = AnimeAdapter()
                                    animeAdapter.setData(recommendationList)
                                    recyclerView.adapter = animeAdapter
                                    shimmerLayout.visibility = View.GONE
                                    recyclerView.visibility = View.VISIBLE
                                }
                            }
                            is ScreenStateHelper.Error -> {
                                emptyText.text = animeList.message
                                emptyText.visibility = View.VISIBLE
                                recyclerView.visibility = View.GONE
                            }
                            else -> {

                            }
                        }
                    })
                }
                "recommendationManga" -> {
                    recommendationViewModel.getRecommendationApi("manga", malID)
                    recommendationViewModel.mangaList.observe(viewLifecycleOwner, { mangaList ->
                        when(mangaList) {
                            is ScreenStateHelper.Loading -> {

                            }
                            is ScreenStateHelper.Success -> {
                                if(mangaList.data != null) {
                                    val recommendationList = mangaList.data
                                    recyclerView.layoutManager =
                                        GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                                    mangaAdapter = MangaAdapter()
                                    mangaAdapter.setData(recommendationList)
                                    recyclerView.adapter = mangaAdapter
                                    shimmerLayout.visibility = View.GONE
                                    recyclerView.visibility = View.VISIBLE
                                }
                            }
                            is ScreenStateHelper.Error -> {
                                emptyText.text = mangaList.message
                                emptyText.visibility = View.VISIBLE
                                recyclerView.visibility = View.GONE
                            }
                            else -> {

                            }
                        }
                    })
                }
                "actorAnime" -> {
                    actorViewModel.getActorAnimeApi(malID)
                    actorViewModel.actorAnimeList.observe(viewLifecycleOwner, { actorAnime ->
                        when(actorAnime) {
                            is ScreenStateHelper.Loading -> {

                            }
                            is ScreenStateHelper.Success -> {
                                if(actorAnime.data != null) {
                                    val actorAnimeList = actorAnime.data
                                    recyclerView.layoutManager =
                                        GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                                    animeAdapter = AnimeAdapter()
                                    animeAdapter.setData(actorAnimeList)
                                    recyclerView.adapter = animeAdapter
                                    shimmerLayout.visibility = View.GONE
                                    recyclerView.visibility = View.VISIBLE
                                }
                            }
                            else -> {

                            }
                        }
                    })
                }
            }
        }
    }
}