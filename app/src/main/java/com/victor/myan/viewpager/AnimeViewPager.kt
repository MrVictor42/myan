package com.victor.myan.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.victor.myan.fragments.tablayouts.anime.CharacterFragment
import com.victor.myan.fragments.tablayouts.anime.OverviewAnimeFragment
import com.victor.myan.fragments.RecommendationFragment

class AnimeViewPager(fragment : FragmentManager, lifecycle : Lifecycle, private val malID : Int, private val size : Int) : FragmentStateAdapter(fragment, lifecycle) {

    override fun getItemCount(): Int {
        return size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> OverviewAnimeFragment.newInstance(malID)
            1 -> CharacterFragment.newInstance(malID)
            2 -> RecommendationFragment.newInstance("anime", malID)
            else -> Fragment()
        }
    }
}