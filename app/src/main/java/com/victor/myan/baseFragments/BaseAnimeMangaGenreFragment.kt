package com.victor.myan.baseFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.victor.myan.R
import com.victor.myan.databinding.FragmentBaseAnimeMangaGenreBinding
import com.victor.myan.viewpager.GenreTypeViewPager

class BaseAnimeMangaGenreFragment(
    private val malID : Int, private val selected : String
) : Fragment() {

    private lateinit var binding : FragmentBaseAnimeMangaGenreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseAnimeMangaGenreBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager
        val sizePager = 3
        val adapter = GenreTypeViewPager(parentFragmentManager, lifecycle, malID, sizePager, selected)

        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false
        TabLayoutMediator(tabLayout, viewPager, true, false){ tab, position ->
            when(position) {
                0 -> tab.text = "Airing"
                1 -> tab.text = "Complete"
                2 -> tab.text = "Upcoming"
            }
        }.attach()
    }
}