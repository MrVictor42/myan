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
import com.victor.myan.databinding.FragmentBaseMangaBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.viewmodel.PictureViewModel
import com.victor.myan.viewpager.MangaViewPager

class BaseMangaFragment(
    private val malID: Int
) : Fragment() {

    private lateinit var binding : FragmentBaseMangaBinding
    private val pictureViewModel by lazy {
        ViewModelProvider(this)[PictureViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseMangaBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager2
        val sizePager = 2
        val carouselView = binding.carouselView
        val shimmerLayout = binding.shimmerLayout
        val adapter = MangaViewPager(parentFragmentManager, lifecycle, malID, sizePager)

        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false
        TabLayoutMediator(tabLayout, viewPager, true, false) {tab, position ->
            when(position) {
                0 -> tab.text = "Overview"
                1 -> tab.text = "Recommendation"
            }
        }.attach()

        pictureViewModel.getPicturesApi("manga", malID)
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
                    if(picturesList.message == "timeout" || picturesList.message == "423") {
                        carouselView.visibility = View.GONE
                        shimmerLayout.stopShimmer()
                        shimmerLayout.visibility = View.INVISIBLE
                    }
                }
                else -> {
                    // Nothing to do
                }
            }
        })
    }
}