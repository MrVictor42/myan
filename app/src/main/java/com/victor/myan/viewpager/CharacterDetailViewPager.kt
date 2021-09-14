package com.victor.myan.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.victor.myan.fragments.tablayouts.characterDetail.CharacterAnimeFragment
import com.victor.myan.fragments.tablayouts.characterDetail.MangaCharacterFragment
import com.victor.myan.fragments.tablayouts.characterDetail.OverviewCharacterFragment
import com.victor.myan.fragments.tablayouts.characterDetail.VoiceCharacterFragment

class CharacterDetailViewPager(fragment : FragmentManager, lifecycle : Lifecycle, mal_id : Int, sizePager : Int) : FragmentStateAdapter(fragment, lifecycle) {

    private val malID = mal_id
    private val size = sizePager

    override fun getItemCount(): Int {
        return size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OverviewCharacterFragment.newInstance(malID)
            1 -> CharacterAnimeFragment.newInstance(malID)
            2 -> MangaCharacterFragment.newInstance(malID)
            3 -> VoiceCharacterFragment.newInstance(malID)
            else -> Fragment()
        }
    }
}