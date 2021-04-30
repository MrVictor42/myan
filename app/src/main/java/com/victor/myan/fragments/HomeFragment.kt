package com.victor.myan.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import com.victor.myan.R
import com.victor.myan.databinding.FragmentHomeBinding
import com.victor.myan.fragmentsTab.DayAnimesFragment
import com.victor.myan.fragmentsTab.SeasonAnimeFragment
import com.victor.myan.fragmentsTab.TopAnimesFragment

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
        val adapter = FragmentPagerItemAdapter(fragmentManager, FragmentPagerItems.with(context)
            .add("Day Animes", DayAnimesFragment::class.java)
            .add("Season Animes", SeasonAnimeFragment::class.java)
            .add("Top Animes", TopAnimesFragment::class.java)
            .create()
        )

        binding.viewpager.adapter = adapter
        binding.viewpagertab.setViewPager(binding.viewpager)
    }
}