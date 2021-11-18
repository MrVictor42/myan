package com.victor.myan.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.victor.myan.fragments.tablayouts.lists.personalList.PersonalListAnime
import com.victor.myan.fragments.tablayouts.lists.personalList.PersonalListManga

class PersonalListViewPager(fragment : FragmentManager, lifecycle : Lifecycle, private val idList : String, private val size : Int) : FragmentStateAdapter(fragment, lifecycle) {

    override fun getItemCount(): Int {
        return size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> PersonalListAnime.newInstance(idList)
            1 -> PersonalListManga.newInstance(idList)
            else -> Fragment()
        }
    }
}