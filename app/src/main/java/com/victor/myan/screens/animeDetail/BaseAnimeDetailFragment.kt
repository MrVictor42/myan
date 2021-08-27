package com.victor.myan.screens.animeDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.victor.myan.R
import com.victor.myan.adapter.AnimeDetailViewPagerAdapter
import com.victor.myan.databinding.FragmentBaseAnimeDetailBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Picture
import com.victor.myan.screens.HomeFragment
import com.victor.myan.viewmodel.AnimePicturesViewModel

class BaseAnimeDetailFragment : Fragment() {

    private lateinit var binding : FragmentBaseAnimeDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseAnimeDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getString("mal_id").toString()
        val viewModel : AnimePicturesViewModel by viewModels { AnimePicturesViewModel.AnimePicturesViewModelFactory(malID) }

        viewModel.animePicturesLiveData.observe(this, { state ->
            processAnimePictureResponse(state)
        })
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager2
        val sizePager = 3
        val adapter = AnimeDetailViewPagerAdapter(parentFragmentManager, lifecycle, malID, sizePager)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager){tab, position ->
            when(position) {
                0 -> tab.text = "Overview"
                1 -> tab.text = "Characters"
                2 -> tab.text = "Recommendation"
            }
        }.attach()

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

    private fun processAnimePictureResponse(state: ScreenStateHelper<List<Picture>?>?) {

        val progressBar = binding.progressBar
        val carouselView = binding.carouselView.carouselViewCarousel

        when(state) {
            is ScreenStateHelper.Loading -> {
                progressBar.visibility = View.VISIBLE
            }
            is ScreenStateHelper.Success -> {
                if (state.data != null) {
                    for(pictures in state.data.indices) {
                        carouselView.setViewListener { position ->
                            val viewListener = layoutInflater.inflate(
                                R.layout.carousel_custom,
                                null
                            )

                            val animeImage =
                                viewListener.findViewById<ImageView>(R.id.anime_image_carousel)
                            Glide.with(view?.context!!).load(state.data[position].large).into(animeImage)
                            viewListener
                        }
                    }
                }
            }
            is ScreenStateHelper.Error -> {
                progressBar.visibility = View.VISIBLE
                val view = progressBar.rootView
                Snackbar.make(view, "Not found information about this character...", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}