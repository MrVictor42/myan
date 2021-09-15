package com.victor.myan.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.victor.myan.fragments.tablayouts.animeDetail.CharacterFragment
import com.victor.myan.fragments.tablayouts.animeDetail.OverviewAnimeFragment
import com.victor.myan.fragments.tablayouts.animeDetail.RecommendationFragment

class AnimeDetailViewPager(fragment : FragmentManager, lifecycle : Lifecycle, mal_id : Int, sizePager : Int) : FragmentStateAdapter(fragment, lifecycle) {

    private val malID = mal_id
    private val size = sizePager

    override fun getItemCount(): Int {
        return size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> OverviewAnimeFragment.newInstance(malID)
            1 -> CharacterFragment.newInstance(malID)
            2 -> RecommendationFragment.newInstance(malID)
            else -> Fragment()
        }
    }
}