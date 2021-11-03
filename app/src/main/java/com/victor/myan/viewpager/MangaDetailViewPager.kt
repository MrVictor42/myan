package com.victor.myan.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.victor.myan.fragments.tablayouts.mangaDetail.MangaCharacterFragment
import com.victor.myan.fragments.tablayouts.mangaDetail.MangaRecommendationFragment
import com.victor.myan.fragments.tablayouts.mangaDetail.OverviewMangaFragment

class MangaDetailViewPager(fragment : FragmentManager, lifecycle : Lifecycle, private val malID : Int, private val size : Int) : FragmentStateAdapter(fragment, lifecycle) {

    override fun getItemCount(): Int {
        return size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> OverviewMangaFragment.newInstance(malID)
            1 -> MangaCharacterFragment.newInstance(malID)
            2 -> MangaRecommendationFragment.newInstance(malID)
            else -> Fragment()
        }
    }
}