package com.victor.myan.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.victor.myan.fragments.tablayouts.listsDetail.season.SeasonFragment

class SeasonViewPager(fragment : FragmentManager, lifecycle : Lifecycle, private val size : Int) : FragmentStateAdapter(fragment, lifecycle) {

    override fun getItemCount(): Int {
        return size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SeasonFragment.newInstance("winter")
            1 -> SeasonFragment.newInstance("spring")
            2 -> SeasonFragment.newInstance("summer")
            3 -> SeasonFragment.newInstance("fall")
            else -> Fragment()
        }
    }
}