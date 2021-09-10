package com.victor.myan.baseFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.victor.myan.R
import com.victor.myan.adapter.ActorDetailViewPagerAdapter
import com.victor.myan.databinding.FragmentBaseActorDetailBinding
import com.victor.myan.fragments.HomeFragment
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Picture
import com.victor.myan.viewmodel.PictureViewModel

class BaseActorDetailFragment : Fragment() {

    private lateinit var binding : FragmentBaseActorDetailBinding
    private val pictureViewModel by lazy {
        ViewModelProvider(this).get(PictureViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseActorDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getString("mal_id").toString()
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager2
        val sizePager = 3
        val adapter = ActorDetailViewPagerAdapter(parentFragmentManager, lifecycle, malID, sizePager)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager){tab, position ->
            when(position) {
                0 -> tab.text = "Overview"
                1 -> tab.text = "Anime"
                2 -> tab.text = "Character"
            }
        }.attach()

//        pictureViewModel.getPicturesApi("person", malID)
//            processPictureResponse(state)
//        })

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