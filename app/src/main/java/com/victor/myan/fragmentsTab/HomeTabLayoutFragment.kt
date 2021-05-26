package com.victor.myan.fragmentsTab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import com.victor.myan.R
import com.victor.myan.databinding.FragmentHomeBinding
import com.victor.myan.databinding.FragmentHomeTabLayoutBinding
import com.victor.myan.fragments.HomeFragment
import com.victor.myan.fragments.SearchFragment

class HomeTabLayoutFragment : Fragment() {

    private var _binding: FragmentHomeTabLayoutBinding ? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(): HomeTabLayoutFragment {
            val homeFragment = HomeTabLayoutFragment()
            val args = Bundle()
            homeFragment.arguments = args
            return homeFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeTabLayoutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = FragmentPagerItemAdapter(fragmentManager, FragmentPagerItems.with(context)

            .add("Home", HomeFragment.newInstance()::class.java)
            .add("Search", SearchFragment.newInstance()::class.java)
            .create())

        val viewPager2 = binding.viewpager
        val viewpagertab = binding.viewPagerTab

        viewPager2.adapter = adapter
        viewpagertab.setViewPager(viewPager2)
    }
}