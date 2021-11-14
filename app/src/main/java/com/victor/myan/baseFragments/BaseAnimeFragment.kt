package com.victor.myan.baseFragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.victor.myan.R
import com.victor.myan.databinding.FragmentBaseAnimeBinding
import com.victor.myan.viewpager.AnimeViewPager
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.viewmodel.PictureViewModel

class BaseAnimeFragment : Fragment() {

    private lateinit var binding : FragmentBaseAnimeBinding
    private val pictureViewModel by lazy {
        ViewModelProvider(this)[PictureViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseAnimeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getInt("mal_id")!!
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager2
        val sizePager = 3
        val carouselView = binding.carouselView
        val shimmerLayout = binding.shimmerLayout
        val adapter = AnimeViewPager(parentFragmentManager, lifecycle, malID, sizePager)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager, true, false) { tab, position ->
            when(position) {
                0 -> tab.text = "Overview"
                1 -> tab.text = "Character"
                2 -> tab.text = "Recommendation"
            }
        }.attach()

        pictureViewModel.getPicturesApi("anime", malID)
        pictureViewModel.pictureList.observe(viewLifecycleOwner, { picturesList ->
            when(picturesList) {
                is ScreenStateHelper.Loading -> {
                    shimmerLayout.visibility = View.VISIBLE
                }
                is ScreenStateHelper.Success -> {
                    if (picturesList.data != null) {
                        for(pictures in picturesList.data.indices) {
                            carouselView.setViewListener { position ->
                                val viewListener = layoutInflater.inflate(
                                    R.layout.fragment_carousel_anime_list,
                                    null
                                )

                                val animeImage =
                                    viewListener.findViewById<ImageView>(R.id.image_carousel)
                                Glide.with(view.context!!).load(picturesList.data[position].large).into(animeImage)
                                viewListener
                            }
                        }
                        carouselView.pageCount = picturesList.data.size
                        carouselView.visibility = View.VISIBLE
                        shimmerLayout.stopShimmer()
                        shimmerLayout.visibility = View.INVISIBLE
                    }
                }
                is ScreenStateHelper.Error -> {

                }
                else -> {
                    // Nothing to do
                }
            }
        })
    }
}