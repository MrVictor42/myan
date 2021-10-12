package com.victor.myan.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.victor.myan.fragments.tablayouts.actorDetail.ActorAnimeFragment
import com.victor.myan.fragments.tablayouts.actorDetail.ActorCharacterFragment
import com.victor.myan.fragments.tablayouts.actorDetail.OverviewActorFragment

class ActorDetailViewPager(fragment : FragmentManager, lifecycle : Lifecycle, mal_id : Int, sizePager : Int) : FragmentStateAdapter(fragment, lifecycle) {

    private val malID = mal_id
    private val size = sizePager

    override fun getItemCount(): Int {
        return size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OverviewActorFragment.newInstance(malID)
            1 -> ActorAnimeFragment.newInstance(malID)
            2 -> ActorCharacterFragment.newInstance(malID)
            else -> Fragment()
        }
    }
}