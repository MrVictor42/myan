package com.victor.myan.baseFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.google.android.material.tabs.TabLayoutMediator
import com.victor.myan.R
import com.victor.myan.adapter.ActorDetailViewPagerAdapter
import com.victor.myan.databinding.FragmentBaseActorDetailBinding
import com.victor.myan.fragments.HomeFragment

class BaseActorDetailFragment : Fragment() {

    private lateinit var binding : FragmentBaseActorDetailBinding

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
}