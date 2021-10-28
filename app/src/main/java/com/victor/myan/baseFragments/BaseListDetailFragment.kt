package com.victor.myan.baseFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.victor.myan.R
import com.victor.myan.databinding.FragmentBaseListDetailBinding
import com.victor.myan.fragments.tablayouts.listsDetail.personalList.PersonalListFragment
import com.victor.myan.viewpager.PersonalListViewPager

class BaseListDetailFragment : Fragment() {

    private lateinit var binding : FragmentBaseListDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseListDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val image = arguments?.getString("image")
        val name = arguments?.getString("name")
        val description = arguments?.getString("description")
        val id = arguments?.getString("ID")!!
        val imageList = binding.imagePersonalList
        val nameList = binding.namePersonalList
        val descriptionList = binding.descriptionPersonalList
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager2
        val sizePager = 2
        val adapter = PersonalListViewPager(parentFragmentManager, lifecycle, id, sizePager)

        viewPager.adapter = adapter
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager){tab, position ->
            when(position) {
                0 -> tab.text = "Anime"
                1 -> tab.text = "Manga"
            }
        }.attach()

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val personalList = PersonalListFragment.newInstance()
                val fragmentManager = activity?.supportFragmentManager
                fragmentManager?.popBackStack()
                fragmentManager?.beginTransaction()?.replace(R.id.fragment_layout, personalList)
                    ?.addToBackStack(null)?.commit()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        Glide.with(view.context).load(image).into(imageList)
        nameList.text = name
        descriptionList.text = description
    }
}