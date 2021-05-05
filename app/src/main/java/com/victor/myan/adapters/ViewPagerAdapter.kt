package com.victor.myan.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.victor.myan.fragmentsTab.DayAnimesFragment
import com.victor.myan.fragmentsTab.SeasonAnimeFragment
import com.victor.myan.fragmentsTab.TopAnimesFragment

class ViewPagerAdapter(fragment: FragmentManager, lifecycler: Lifecycle) : FragmentStateAdapter(fragment, lifecycler) {

    private val TOTAL_TABS = 3

    override fun getItemCount(): Int {
        return TOTAL_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                DayAnimesFragment()
            }
            1 -> {
                SeasonAnimeFragment()
            }
            2 -> {
                TopAnimesFragment()
            } else -> {
                Fragment()
            }
        }
    }
}