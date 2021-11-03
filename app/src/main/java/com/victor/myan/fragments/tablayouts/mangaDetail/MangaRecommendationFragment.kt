package com.victor.myan.fragments.tablayouts.mangaDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.R
import com.victor.myan.adapter.AnimeAdapter
import com.victor.myan.databinding.FragmentMangaRecommendationBinding
import com.victor.myan.fragments.tablayouts.animeDetail.RecommendationFragment
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Anime
import com.victor.myan.viewmodel.AnimeViewModel

class MangaRecommendationFragment : Fragment() {

    private lateinit var binding : FragmentMangaRecommendationBinding
    private lateinit var animeAdapter: AnimeAdapter
    private val animeViewModel by lazy {
        ViewModelProvider(this)[AnimeViewModel::class.java]
    }
    private val TAG = MangaRecommendationFragment::class.java.simpleName

    companion object {
        fun newInstance(mal_id : Int): MangaRecommendationFragment {
            val recommendationFragment = MangaRecommendationFragment()
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
        binding = FragmentMangaRecommendationBinding.inflate(layoutInflater, container, false)
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
        val recommendationRecyclerView = binding.recyclerView.recyclerViewVertical
        val shimmerLayoutRecommendation = binding.shimmerLayoutRecommendation

        when(state) {
            is ScreenStateHelper.Loading -> {
                shimmerLayoutRecommendation.startShimmer()
                Log.i(TAG, "Loading Recommendation List Airing")
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
                    shimmerLayoutRecommendation.stopShimmer()
                    shimmerLayoutRecommendation.visibility = View.GONE
                    recommendationRecyclerView.visibility = View.VISIBLE
                    Log.i(TAG, "Success Recommendation List")
                }
            }
            is ScreenStateHelper.Error -> {
                Log.e(TAG, "Error Recommendation List in Recommendation Fragment With Code: ${state.message}")
            }
            else -> {
                // Nothing to do
            }
        }
    }
}