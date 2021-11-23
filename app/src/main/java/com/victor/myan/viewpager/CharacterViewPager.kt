package com.victor.myan.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.victor.myan.fragments.AnimeMangaFragment
import com.victor.myan.fragments.tablayouts.character.*

class CharacterViewPager(
    fragment : FragmentManager, lifecycle : Lifecycle, private val malID: Int, private val size : Int
) : FragmentStateAdapter(fragment, lifecycle) {

    override fun getItemCount(): Int {
        return size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OverviewCharacterFragment.newInstance(malID)
            1 -> AnimeMangaFragment(malID, "characterAnime")
            2 -> AnimeMangaFragment(malID, "characterManga")
            3 -> VoiceFragment.newInstance(malID)
            else -> Fragment()
        }
    }
}