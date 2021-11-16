package com.victor.myan.baseFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.victor.myan.R
import com.victor.myan.databinding.FragmentBaseGenreBinding
import com.victor.myan.fragments.GenreFragment
import com.victor.myan.fragments.HomeFragment
import com.victor.myan.viewpager.GenreViewPager

class BaseGenreFragment : Fragment() {

    private lateinit var binding : FragmentBaseGenreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseGenreBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getInt("mal_id")!!
        val name = arguments?.getString("name")
        val toolbar = binding.toolbar.toolbar
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager
        val sizePager = 2
        val adapter = GenreViewPager(parentFragmentManager, lifecycle, malID, sizePager)

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
                val homeFragment = HomeFragment()
                val fragmentManager = activity?.supportFragmentManager
                fragmentManager?.popBackStack()
                fragmentManager?.beginTransaction()?.replace(R.id.fragment_layout, homeFragment)
                    ?.addToBackStack(null)?.commit()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        toolbar.title = name
        toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        toolbar.setNavigationOnClickListener {
            val genreListFragment = GenreFragment()
            (view.context as FragmentActivity)
                .supportFragmentManager
                .beginTransaction()
                .remove(this)
                .replace(R.id.fragment_layout, genreListFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}