package com.victor.myan.baseFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.victor.myan.R
import com.victor.myan.databinding.FragmentBaseListsBinding
import com.victor.myan.fragments.HomeFragment
import com.victor.myan.viewpager.ListFragmentViewPager

class BaseListsFragment : Fragment() {

    private lateinit var binding : FragmentBaseListsBinding

    companion object {
        fun newInstance(): BaseListsFragment {
            val listFragment = BaseListsFragment()
            val args = Bundle()
            listFragment.arguments = args
            return listFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseListsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager2
        val sizePager = 4
        val adapter = ListFragmentViewPager(parentFragmentManager, lifecycle, sizePager)

        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false
        TabLayoutMediator(tabLayout, viewPager, true, false){ tab, position ->
            when(position) {
                0 -> tab.text = "Personal"
                1 -> tab.text = "Top"
                2 -> tab.text = "Day"
                3 -> tab.text = "Season"
            }
        }.attach()
    }
}