package com.victor.myan.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.victor.myan.screens.animeDetail.Overview
import com.victor.myan.screens.animeDetail.Recommendation

class ViewPagerAdapter(fragment : FragmentManager, lifecycle : Lifecycle, mal_id : String) : FragmentStateAdapter(fragment, lifecycle) {

    private val malID = mal_id

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> Overview.newInstance(malID)
            1 -> Recommendation.newInstance(malID)
            else -> Fragment()
        }
    }
}