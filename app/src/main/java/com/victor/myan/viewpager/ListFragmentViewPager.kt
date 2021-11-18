package com.victor.myan.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.victor.myan.baseFragments.BaseDaysListFragment
import com.victor.myan.baseFragments.BaseSeasonFragment
import com.victor.myan.baseFragments.BaseTopListFragment
import com.victor.myan.fragments.tablayouts.lists.personalList.PersonalListFragment

class ListFragmentViewPager(fragment : FragmentManager, lifecycle : Lifecycle, private val size : Int)
    : FragmentStateAdapter(fragment, lifecycle) {

    override fun getItemCount(): Int {
        return size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> PersonalListFragment.newInstance()
            1 -> BaseTopListFragment.newInstance()
            2 -> BaseDaysListFragment.newInstance()
            3 -> BaseSeasonFragment.newInstance()
            else -> Fragment()
        }
    }
}