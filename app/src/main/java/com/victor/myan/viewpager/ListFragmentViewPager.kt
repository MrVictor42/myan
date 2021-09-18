package com.victor.myan.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.victor.myan.fragments.tablayouts.lists.PersonalListFragment

class ListFragmentViewPager(fragment : FragmentManager, lifecycle : Lifecycle, sizePager : Int)
    : FragmentStateAdapter(fragment, lifecycle) {

    private val size = sizePager

    override fun getItemCount(): Int {
        return size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> PersonalListFragment.newInstance()
            else -> Fragment()
        }
    }
}