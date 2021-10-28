package com.victor.myan.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.victor.myan.fragments.tablayouts.listsDetail.top.AnimeTopFragment
import com.victor.myan.fragments.tablayouts.listsDetail.top.MangaTopFragment

class TopListViewPager(fragment : FragmentManager, lifecycle : Lifecycle, private val sizePager : Int) : FragmentStateAdapter(fragment, lifecycle) {

    override fun getItemCount(): Int {
        return sizePager
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AnimeTopFragment.newInstance()
            1 -> MangaTopFragment.newInstance()
            else -> Fragment()
        }
    }
}