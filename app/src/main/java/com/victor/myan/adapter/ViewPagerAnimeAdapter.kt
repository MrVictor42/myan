package com.victor.myan.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.victor.myan.screens.animeDetail.CharacterStaffFragment
import com.victor.myan.screens.animeDetail.Overview
import com.victor.myan.screens.animeDetail.RecommendationFragment

class ViewPagerAnimeAdapter(fragment : FragmentManager, lifecycle : Lifecycle, mal_id : String, year : String, sizePager : Int) : FragmentStateAdapter(fragment, lifecycle) {

    private val malID = mal_id
    private val animeYear = year
    private val size = sizePager

    override fun getItemCount(): Int {
        return size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> Overview.newInstance(malID, animeYear)
            1 -> CharacterStaffFragment.newInstance(malID)
            2 -> RecommendationFragment.newInstance(malID)
            else -> Fragment()
        }
    }
}