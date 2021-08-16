package com.victor.myan.screens.animeDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.victor.myan.adapter.ViewPagerCharacterStaffAdapter
import com.victor.myan.databinding.FragmentBaseCharactersStaffBinding

class BaseCharactersStaff : Fragment() {

    private lateinit var binding : FragmentBaseCharactersStaffBinding

    companion object {
        fun newInstance(mal_id : String): BaseCharactersStaff {
            val baseCharactersStaff = BaseCharactersStaff()
            val args = Bundle()
            args.putString("mal_id", mal_id)
            baseCharactersStaff.arguments = args
            return baseCharactersStaff
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseCharactersStaffBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getString("mal_id").toString()
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPagerCharacterStaff
        val sizePager = 2
        val adapter = ViewPagerCharacterStaffAdapter(parentFragmentManager, lifecycle, malID, sizePager)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            when(position) {
                0 -> tab.text = "Characters"
                1 -> tab.text = "Staff"
            }
        }.attach()
    }
}