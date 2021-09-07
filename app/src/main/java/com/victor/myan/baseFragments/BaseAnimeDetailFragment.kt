package com.victor.myan.baseFragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.victor.myan.R
import com.victor.myan.adapter.AnimeDetailViewPagerAdapter
import com.victor.myan.databinding.FragmentBaseAnimeDetailBinding
import com.victor.myan.fragments.HomeFragment
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Picture
import com.victor.myan.viewmodel.PictureViewModel

class BaseAnimeDetailFragment : Fragment() {

    private lateinit var binding : FragmentBaseAnimeDetailBinding
    private val pictureViewModel by lazy {
        ViewModelProvider(this).get(PictureViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseAnimeDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getString("mal_id").toString()
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

        pictureViewModel.getPicturesApi("anime", malID)
        pictureViewModel.picture.observe(this, { state ->
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

    private fun processPictureResponse(state: ScreenStateHelper<Picture>?) {

        val image = binding.pictureBase.image
        when(state) {
            is ScreenStateHelper.Loading -> {

            }
            is ScreenStateHelper.Success -> {
                if (state.data != null) {
                    Log.e("state", state.data.large.toString())
                    Glide.with(view?.context!!)
                        .load(state.data.large)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground)
                        .fallback(R.drawable.ic_launcher_foreground)
                        .fitCenter()
                        .into(image)
                }
            }
            is ScreenStateHelper.Error -> {

            }
            else -> {

            }
        }
    }
}