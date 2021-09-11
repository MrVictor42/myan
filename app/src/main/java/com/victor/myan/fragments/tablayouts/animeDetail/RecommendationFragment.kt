package com.victor.myan.fragments.tablayouts.animeDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.adapter.AnimeAdapter
import com.victor.myan.databinding.FragmentRecommendationBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Anime
import com.victor.myan.viewmodel.AnimeViewModel

class RecommendationFragment : Fragment() {

    private lateinit var binding : FragmentRecommendationBinding
    private lateinit var animeAdapter: AnimeAdapter
    private val animeViewModel by lazy {
        ViewModelProvider(this).get(AnimeViewModel::class.java)
    }

    companion object {
        fun newInstance(mal_id : Int): RecommendationFragment {
            val recommendationFragment = RecommendationFragment()
            val args = Bundle()
            args.putInt("mal_id", mal_id)
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

        animeViewModel.getAnimeRecommendationApi(malID)
        animeViewModel.animeRecommendationList.observe(viewLifecycleOwner, { state ->
            processAnimeRecommendationResponse(state)
        })
    }

    private fun processAnimeRecommendationResponse(state: ScreenStateHelper<List<Anime>?>?) {
        val malID = arguments?.getInt("mal_id")!!
        val recommendationRecyclerView = binding.recyclerView.recyclerViewVertical

        when(state) {
            is ScreenStateHelper.Loading -> {

            }
            is ScreenStateHelper.Success -> {
                if(state.data != null) {
                    val animeRecommendation = state.data
                    recommendationRecyclerView.setHasFixedSize(true)
                    recommendationRecyclerView.setItemViewCacheSize(10)
                    animeAdapter = AnimeAdapter()
                    animeAdapter.submitList(animeRecommendation)
                    recommendationRecyclerView.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                    recommendationRecyclerView.adapter = animeAdapter
                }
            }
            is ScreenStateHelper.Error -> {
                animeViewModel.getAnimeRecommendationApi(malID)
            }
            else -> {
                // Nothing to do
            }
        }
    }
}