package com.victor.myan.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.victor.myan.fragments.CharacterFragment
import com.victor.myan.fragments.RecommendationFragment
import com.victor.myan.fragments.tablayouts.manga.OverviewMangaFragment

class MangaViewPager(fragment : FragmentManager, lifecycle : Lifecycle, private val malID : Int, private val size : Int) : FragmentStateAdapter(fragment, lifecycle) {

    override fun getItemCount(): Int {
        return size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> OverviewMangaFragment.newInstance(malID)
            1 -> CharacterFragment.newInstance("manga" ,malID)
            2 -> RecommendationFragment.newInstance(malID)
            else -> Fragment()
        }
    }
}