package com.victor.myan.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.victor.myan.fragments.AnimeMangaFragment
import com.victor.myan.fragments.CharacterFragment
import com.victor.myan.fragments.tablayouts.actor.OverviewActorFragment

class ActorViewPager(
    fragment : FragmentManager, lifecycle : Lifecycle, private val malID : Int, private val size : Int
) : FragmentStateAdapter(fragment, lifecycle) {

    override fun getItemCount(): Int {
        return size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OverviewActorFragment.newInstance(malID)
            1 -> AnimeMangaFragment(malID, "actorAnime")
            2 -> CharacterFragment(malID, "actor")
            else -> Fragment()
        }
    }
}