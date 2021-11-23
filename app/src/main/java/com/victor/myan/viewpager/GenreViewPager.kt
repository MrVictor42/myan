package com.victor.myan.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.victor.myan.baseFragments.BaseAnimeGenreFragment
import com.victor.myan.baseFragments.BaseMangaGenreFragment

class GenreViewPager(
    fragment : FragmentManager, lifecycle : Lifecycle, private val malID : Int, private val size : Int
) :
    FragmentStateAdapter(fragment, lifecycle) {

    override fun getItemCount(): Int {
        return size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> BaseAnimeGenreFragment.newInstance(malID)
            1 -> BaseMangaGenreFragment.newInstance(malID)
            else -> Fragment()
        }
    }
}