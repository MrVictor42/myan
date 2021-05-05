package com.victor.myan.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.victor.myan.R
import com.victor.myan.adapters.ViewPagerAdapter
import com.victor.myan.databinding.FragmentHomeBinding
import com.victor.myan.fragmentsTab.DayAnimesFragment

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    companion object {
        fun newInstance(): HomeFragment {
            val homeFragment = HomeFragment()
            val args = Bundle()
            homeFragment.arguments = args
            return homeFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPageHome
        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)

        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = "Day Animes"
                1 -> tab.text = "Season Animes"
                2 -> tab.text = "Top Animes"
            }
        }.attach()


//        val titles = listOf<String>("Day Animes", "Season Anime", "Top Animes")
//        val viewPagerAdapter = ViewPagerAdapter(childFragmentManager, lifecycle)
//        viewPager.adapter = viewPagerAdapter
//
//        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            when(position) {
//                0 -> {
//                    tab.text
//                }
//            }
//        }

//        viewPager.adapter = viewPagerAdapter
//        tabLayout.setupWithViewPager(viewPager)
    }
}