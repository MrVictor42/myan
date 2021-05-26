package com.victor.myan.fragmentsTab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.myan.controller.SeasonAnimeController
import com.victor.myan.controller.TodayAnimeController
import com.victor.myan.controller.TopAnimeController
import com.victor.myan.databinding.FragmentHomeTabBinding

class HomeTabFragment : Fragment() {

    private lateinit var binding : FragmentHomeTabBinding
    private val todayAnimeController = TodayAnimeController()
    private val seasonAnimeController = SeasonAnimeController()
    private val topAnimeController = TopAnimeController()

    companion object {
        fun newInstance(): HomeTabFragment {
            val homeTabFragment = HomeTabFragment()
            val args = Bundle()
            homeTabFragment.arguments = args
            return homeTabFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeTabBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        todayAnimeController.getTodayAnime(view)
        seasonAnimeController.getSeasonAnime(view)
        topAnimeController.getTopAnime(view)
    }
}