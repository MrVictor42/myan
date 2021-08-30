package com.victor.myan.screens.personDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.victor.myan.R
import com.victor.myan.adapter.PersonDetailViewPagerAdapter
import com.victor.myan.databinding.FragmentBasePersonDetailBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Picture
import com.victor.myan.screens.HomeFragment
import com.victor.myan.viewmodel.PicturesViewModel

class BasePersonDetailFragment : Fragment() {

    private lateinit var binding : FragmentBasePersonDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBasePersonDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getString("mal_id").toString()
        val viewModel : PicturesViewModel by viewModels { PicturesViewModel.PicturesViewModelFactory("person", malID) }
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager2
        val sizePager = 3
        val adapter = PersonDetailViewPagerAdapter(parentFragmentManager, lifecycle, malID, sizePager)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager){tab, position ->
            when(position) {
                0 -> tab.text = "Overview"
                1 -> tab.text = "Anime"
                2 -> tab.text = "Character"
            }
        }.attach()

        viewModel.picturesLiveData.observe(this, { state ->
            processPersonPictureResponse(state)
        })

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val homeFragment = HomeFragment.newInstance()
                val fragmentManager = activity?.supportFragmentManager
                fragmentManager?.popBackStack()
                fragmentManager?.beginTransaction()?.replace(R.id.content, homeFragment)
                    ?.addToBackStack(null)?.commit()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun processPersonPictureResponse(state: ScreenStateHelper<List<Picture>?>?) {

        val carouselView = binding.carouselView.carouselViewCarousel

        when(state) {
            is ScreenStateHelper.Loading -> {

            }
            is ScreenStateHelper.Success -> {
                if (state.data != null) {
                    for(pictures in state.data.indices) {
                        carouselView.setViewListener { position ->
                            val viewListener = layoutInflater.inflate(
                                R.layout.fragment_carousel_anime_list,
                                null
                            )

                            val animeImage =
                                viewListener.findViewById<ImageView>(R.id.anime_image_carousel)
                            Glide.with(view?.context!!).load(state.data[position].large).into(animeImage)
                            viewListener
                        }
                    }
                    carouselView.pageCount = state.data.size
                }
            }
            is ScreenStateHelper.Error -> {

            }
            else -> {
                // Nothing to do
            }
        }
    }
}