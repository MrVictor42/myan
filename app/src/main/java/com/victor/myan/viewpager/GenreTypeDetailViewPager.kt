package com.victor.myan.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.victor.myan.fragments.tablayouts.genreDetail.AiringFragment
import com.victor.myan.fragments.tablayouts.genreDetail.CompleteFragment
import com.victor.myan.fragments.tablayouts.genreDetail.UpcomingFragment

class GenreTypeDetailViewPager(fragment : FragmentManager, lifecycle : Lifecycle, mal_id : Int, sizePager : Int, type : String) :
    FragmentStateAdapter(fragment, lifecycle) {

    private val malID = mal_id
    private val size = sizePager
    private val selected = type

    override fun getItemCount(): Int {
        return size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> AiringFragment.newInstance(malID, selected)
            1 -> CompleteFragment.newInstance(malID, selected)
            2 -> UpcomingFragment.newInstance(malID, selected)
            else -> Fragment()
        }
    }
}