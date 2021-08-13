package com.victor.myan.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.victor.myan.screens.animeDetail.Overview
import com.victor.myan.screens.animeDetail.Recommendation

class ViewPagerAdapter(fragment : FragmentManager, lifecycle : Lifecycle) : FragmentStateAdapter(fragment, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> Overview()
            1 -> Recommendation()
            else -> Fragment()
        }
    }
}