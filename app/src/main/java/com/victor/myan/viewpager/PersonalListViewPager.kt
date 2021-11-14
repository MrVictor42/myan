package com.victor.myan.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.victor.myan.fragments.tablayouts.lists.personalList.PersonalListAnime
import com.victor.myan.fragments.tablayouts.lists.personalList.PersonalListManga

class PersonalListViewPager(fragment : FragmentManager, lifecycle : Lifecycle, id : String, sizePager : Int) : FragmentStateAdapter(fragment, lifecycle) {

    private val size = sizePager
    private val idList = id

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