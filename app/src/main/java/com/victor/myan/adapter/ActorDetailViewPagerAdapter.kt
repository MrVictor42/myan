package com.victor.myan.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.victor.myan.fragments.tablayouts.actorDetail.ActorAnimeFragment
import com.victor.myan.fragments.tablayouts.actorDetail.OverviewPersonFragment

class ActorDetailViewPagerAdapter(fragment : FragmentManager, lifecycle : Lifecycle, mal_id : String, sizePager : Int) : FragmentStateAdapter(fragment, lifecycle) {

    private val malID = mal_id
    private val size = sizePager

    override fun getItemCount(): Int {
        return size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OverviewPersonFragment.newInstance(malID)
            1 -> ActorAnimeFragment.newInstance(malID)
            else -> Fragment()
        }
    }
}