package com.victor.myan.baseFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.victor.myan.R
import com.victor.myan.viewpager.CharacterDetailViewPager
import com.victor.myan.databinding.FragmentBaseCharacterDetailBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Picture
import com.victor.myan.fragments.HomeFragment
import com.victor.myan.viewmodel.PictureViewModel

class BaseCharacterDetailFragment : Fragment() {

    private lateinit var binding : FragmentBaseCharacterDetailBinding
    private val pictureViewModel by lazy {
        ViewModelProvider(this).get(PictureViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseCharacterDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getInt("mal_id")!!
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager2
        val sizePager = 4
        val adapter = CharacterDetailViewPager(parentFragmentManager, lifecycle, malID, sizePager)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager){tab, position ->
            when(position) {
                0 -> tab.text = "Overview"
                1 -> tab.text = "Anime"
                2 -> tab.text = "Manga"
                3 -> tab.text = "Voices"
            }
        }.attach()

        pictureViewModel.getPicturesApi("character", malID)
        pictureViewModel.pictureList.observe(viewLifecycleOwner, { state ->
            processPictureResponse(state)
        })

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val homeFragment = HomeFragment.newInstance()
                val fragmentManager = activity?.supportFragmentManager
                fragmentManager?.popBackStack()
                fragmentManager?.beginTransaction()?.replace(R.id.fragment_layout, homeFragment)
                    ?.addToBackStack(null)?.commit()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun processPictureResponse(state: ScreenStateHelper<List<Picture>?>?) {
        val carouselView = binding.carouselView.carouselViewCarousel
        val progressBar = binding.carouselView.progressBarCarousel

        when(state) {
            is ScreenStateHelper.Loading -> {
                progressBar.visibility = View.VISIBLE
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
                progressBar.visibility = View.GONE
            }
            is ScreenStateHelper.Error -> {

            }
            else -> {
                // Nothing to do
            }
        }
    }
}