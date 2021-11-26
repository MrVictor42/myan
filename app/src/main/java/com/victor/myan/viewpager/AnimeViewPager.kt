package com.victor.myan.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.victor.myan.fragments.AnimeMangaFragment
import com.victor.myan.fragments.CharacterFragment
import com.victor.myan.fragments.tablayouts.anime.EpisodesAnimeFragment
import com.victor.myan.fragments.tablayouts.anime.OverviewAnimeFragment
import com.victor.myan.fragments.tablayouts.anime.PromoAnimeFragment

class AnimeViewPager(
    fragment : FragmentManager, lifecycle : Lifecycle, private val malID : Int, private val size : Int
) : FragmentStateAdapter(fragment, lifecycle) {

    override fun getItemCount(): Int {
        return size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> OverviewAnimeFragment.newInstance(malID)
            1 -> CharacterFragment(malID, "anime")
            2 -> EpisodesAnimeFragment(malID)
            3 -> PromoAnimeFragment(malID)
            4 -> AnimeMangaFragment(malID, "recommendationAnime")
            else -> Fragment()
        }
    }
}