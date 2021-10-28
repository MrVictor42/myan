package com.victor.myan.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.victor.myan.fragments.tablayouts.listsDetail.day.DayFragment

class DaysViewPager(fragment : FragmentManager, lifecycle : Lifecycle, private val size : Int) : FragmentStateAdapter(fragment, lifecycle) {

    override fun getItemCount(): Int {
        return size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DayFragment.newInstance("Sunday")
            1 -> DayFragment.newInstance("Monday")
            2 -> DayFragment.newInstance("Tuesday")
            3 -> DayFragment.newInstance("Wednesday")
            4 -> DayFragment.newInstance("Thursday")
            5 -> DayFragment.newInstance("Friday")
            6 -> DayFragment.newInstance("Saturday")
            else -> Fragment()
        }
    }
}