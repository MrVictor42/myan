package com.victor.myan.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.victor.myan.screens.animeDetail.Character
import com.victor.myan.screens.animeDetail.Staff

class ViewPagerCharacterStaffAdapter(fragment : FragmentManager, lifecycle : Lifecycle, mal_id : String, sizePager : Int) : FragmentStateAdapter(fragment, lifecycle) {

    private val malID = mal_id
    private val size = sizePager

    override fun getItemCount(): Int {
        return size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> Character.newInstance(malID)
            1 -> Staff.newInstance(malID)
            else -> Fragment()
        }
    }
}