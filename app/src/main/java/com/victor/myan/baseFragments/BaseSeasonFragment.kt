package com.victor.myan.baseFragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.activity.OnBackPressedCallback
import com.google.android.material.tabs.TabLayoutMediator
import com.victor.myan.R
import com.victor.myan.databinding.FragmentBaseSeasonBinding
import com.victor.myan.fragments.HomeFragment
import com.victor.myan.viewpager.SeasonViewPager

class BaseSeasonFragment : Fragment() {

    private lateinit var binding : FragmentBaseSeasonBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseSeasonBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(): BaseSeasonFragment {
            val baseSeasonFragment = BaseSeasonFragment()
            val args = Bundle()
            baseSeasonFragment.arguments = args
            return baseSeasonFragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager2
        val sizePager = 4
        val adapter = SeasonViewPager(parentFragmentManager, lifecycle, sizePager)

        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false
        TabLayoutMediator(tabLayout, viewPager, true, false) { tab, position ->
            when (position) {
                0 -> tab.text = "Winter"
                1 -> tab.text = "Spring"
                2 -> tab.text = "Summer"
                3 -> tab.text = "Fall"
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