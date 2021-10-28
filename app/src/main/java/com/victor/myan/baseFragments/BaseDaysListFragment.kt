package com.victor.myan.baseFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.google.android.material.tabs.TabLayoutMediator
import com.victor.myan.R
import com.victor.myan.databinding.FragmentBaseDaysListBinding
import com.victor.myan.fragments.HomeFragment
import com.victor.myan.viewpager.DaysViewPager

class BaseDaysListFragment : Fragment() {

    private lateinit var binding : FragmentBaseDaysListBinding

    companion object {
        fun newInstance(): BaseDaysListFragment {
            val baseDaysListFragment = BaseDaysListFragment()
            val args = Bundle()
            baseDaysListFragment.arguments = args
            return baseDaysListFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseDaysListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager2
        val sizePager = 7
        val adapter = DaysViewPager(parentFragmentManager, lifecycle, sizePager)

        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager, true, false) { tab, position ->
            when (position) {
                0 -> tab.text = "Sunday"
                1 -> tab.text = "Monday"
                2 -> tab.text = "Tuesday"
                3 -> tab.text = "Wednesday"
                4 -> tab.text = "Thursday"
                5 -> tab.text = "Friday"
                6 -> tab.text = "Saturday"
            }
        }.attach()

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val homeFragment = HomeFragment()
                val fragmentManager = activity?.supportFragmentManager
                fragmentManager?.popBackStack()
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_layout, homeFragment)
                    ?.addToBackStack(null)?.commit()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }
}