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
import com.victor.myan.databinding.FragmentRecommendationBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.viewmodel.RecommendationViewModel

class RecommendationFragment : Fragment() {

    private lateinit var binding : FragmentRecommendationBinding
    private lateinit var animeAdapter: AnimeAdapter
    private lateinit var mangaAdapter: MangaAdapter
    private val recommendationViewModel by lazy {
        ViewModelProvider(this)[RecommendationViewModel::class.java]
    }

    companion object {
        fun newInstance(type : String, mal_id : Int): RecommendationFragment {
            val recommendationFragment = RecommendationFragment()
            val args = Bundle()
            args.putInt("mal_id", mal_id)
            args.putString("type", type)
            recommendationFragment.arguments = args
            return recommendationFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecommendationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getInt("mal_id")!!
        val type = arguments?.getString("type")!!
        val recommendationRecyclerView = binding.recyclerView
        val shimmerLayout = binding.shimmerLayout

        when(type) {
            "anime" -> {
                recommendationViewModel.getRecommendationApi("anime", malID)
                recommendationViewModel.animeList.observe(viewLifecycleOwner, { animeList ->
                    when(animeList) {
                        is ScreenStateHelper.Loading -> {

                        }
                        is ScreenStateHelper.Success -> {
                            if(animeList.data != null) {
                                val recommendationList = animeList.data
                                recommendationRecyclerView.layoutManager =
                                    GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                                animeAdapter = AnimeAdapter()
                                animeAdapter.setData(recommendationList)
                                recommendationRecyclerView.adapter = animeAdapter
                                shimmerLayout.visibility = View.GONE
                                recommendationRecyclerView.visibility = View.VISIBLE
                            }
                        }
                        is ScreenStateHelper.Error -> {

                        }
                        else -> {

                        }
                    }
                })
            }
            "manga" -> {
                recommendationViewModel.getRecommendationApi("manga", malID)
                recommendationViewModel.mangaList.observe(viewLifecycleOwner, { mangaList ->
                    when(mangaList) {
                        is ScreenStateHelper.Loading -> {

                        }
                        is ScreenStateHelper.Success -> {
                            if(mangaList.data != null) {
                                val recommendationList = mangaList.data
                                recommendationRecyclerView.layoutManager =
                                    GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                                mangaAdapter = MangaAdapter()
                                mangaAdapter.setData(recommendationList)
                                recommendationRecyclerView.adapter = mangaAdapter
                                shimmerLayout.visibility = View.GONE
                                recommendationRecyclerView.visibility = View.VISIBLE
                            }
                        }
                        is ScreenStateHelper.Error -> {

                        }
                        else -> {

                        }
                    }
                })
            }
        }
    }
}