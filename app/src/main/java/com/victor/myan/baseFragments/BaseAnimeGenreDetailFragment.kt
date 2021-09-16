package com.victor.myan.baseFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.victor.myan.databinding.FragmentBaseAnimeGenreDetailBinding
import com.victor.myan.viewpager.GenreTypeDetailViewPager

class BaseAnimeGenreDetailFragment : Fragment() {

    private lateinit var binding : FragmentBaseAnimeGenreDetailBinding

    companion object {
        fun newInstance(mal_id : Int): BaseAnimeGenreDetailFragment {
            val baseAnimeGenreDetailFragment = BaseAnimeGenreDetailFragment()
            val args = Bundle()
            args.putInt("mal_id", mal_id)
            baseAnimeGenreDetailFragment.arguments = args
            return baseAnimeGenreDetailFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseAnimeGenreDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getInt("mal_id")!!
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager
        val sizePager = 3
        val adapter = GenreTypeDetailViewPager(parentFragmentManager, lifecycle, malID, sizePager, "anime")

        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager){tab, position ->
            when(position) {
                0 -> tab.text = "Airing"
                1 -> tab.text = "Complete"
                2 -> tab.text = "Upcoming"
            }
        }.attach()
    }
}