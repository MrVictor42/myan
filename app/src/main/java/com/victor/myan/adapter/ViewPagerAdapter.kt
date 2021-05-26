package com.victor.myan.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.victor.myan.fragments.SearchFragment
import com.victor.myan.fragmentsTab.HomeTabFragment

class ViewPagerAdapter(fragment: FragmentManager, lifecycler: Lifecycle) : FragmentStateAdapter(fragment, lifecycler) {

    private val TOTALTABS = 3

    override fun getItemCount(): Int {
        return TOTALTABS
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                HomeTabFragment.newInstance()
            }
            1 -> {
                SearchFragment.newInstance()
            }
            else -> {
                Fragment()
            }
        }
    }
}