package com.victor.myan.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.victor.myan.fragments.tablayouts.genre.OptionsGenres

class GenreTypeViewPager(
    fragment : FragmentManager, lifecycle : Lifecycle,
    private val malID : Int, private val size : Int, private val selected : String
) :
    FragmentStateAdapter(fragment, lifecycle) {

    override fun getItemCount(): Int {
        return size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> OptionsGenres(malID, selected, "airing")
            1 -> OptionsGenres(malID, selected, "complete")
            2 -> OptionsGenres(malID, selected, "upcoming")
            else -> Fragment()
        }
    }
}