package com.victor.myan.baseFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.victor.myan.databinding.FragmentBaseTopListBinding
import com.victor.myan.viewpager.TopListViewPager

class BaseTopListFragment : Fragment() {

    private lateinit var binding : FragmentBaseTopListBinding

    companion object {
        fun newInstance(): BaseTopListFragment {
            val baseTopListFragment = BaseTopListFragment()
            val args = Bundle()
            baseTopListFragment.arguments = args
            return baseTopListFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseTopListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager2
        val sizePager = 2
        val adapter = TopListViewPager(parentFragmentManager, lifecycle, sizePager)

        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false
        TabLayoutMediator(tabLayout, viewPager, true, false){ tab, position ->
            when(position) {
                0 -> tab.text = "Anime"
                1 -> tab.text = "Manga"
            }
        }.attach()
    }
}