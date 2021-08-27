package com.victor.myan.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class CharacterDetailViewPagerAdapter(fragment : FragmentManager, lifecycle : Lifecycle, mal_id : String, sizePager : Int) : FragmentStateAdapter(fragment, lifecycle) {

    private val malID = mal_id
    private val size = sizePager

    override fun getItemCount(): Int {
        return size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
//            0 -> OverviewFragment.newInstance(malID)
//            1 -> CharacterFragment.newInstance(malID)
//            2 -> RecommendationFragment.newInstance(malID)
            else -> Fragment()
        }
    }
}