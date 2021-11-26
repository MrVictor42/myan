package com.victor.myan.fragments.tablayouts.anime

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.victor.myan.adapter.VideosAdapter
import com.victor.myan.databinding.FragmentPromoAnimeBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.viewmodel.AnimeViewModel

class PromoAnimeFragment (
    private val malID: Int
) : Fragment() {

    private lateinit var binding : FragmentPromoAnimeBinding
    private lateinit var videosAdapter: VideosAdapter
    private val animeViewModel by lazy {
        ViewModelProvider(this)[AnimeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPromoAnimeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val shimmerLayout = binding.shimmerLayout
        val emptyList = binding.emptyList
        val recyclerView = binding.recyclerView

        animeViewModel.getAnimePromo(malID)
        animeViewModel.animeListPromo.observe(viewLifecycleOwner, { promo ->
            when(promo) {
                is ScreenStateHelper.Loading -> {
                    shimmerLayout.startShimmer()
                }
                is ScreenStateHelper.Success -> {
                    if(promo.data != null) {
                        val promoList = promo.data
                        videosAdapter = VideosAdapter(promoList)
                        recyclerView.adapter = videosAdapter
                        shimmerLayout.stopShimmer()
                        shimmerLayout.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                    }
                }
                is ScreenStateHelper.Error -> {
                    emptyList.text = promo.message
                    emptyList.visibility = View.VISIBLE
                    shimmerLayout.stopShimmer()
                    shimmerLayout.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                }
                is ScreenStateHelper.Empty -> {
                    emptyList.text = promo.message
                    emptyList.visibility = View.VISIBLE
                    shimmerLayout.stopShimmer()
                    shimmerLayout.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                }
            }
        })
    }
}