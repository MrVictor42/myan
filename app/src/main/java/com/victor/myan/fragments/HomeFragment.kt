package com.victor.myan.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.myan.controller.SeasonAnimeController
import com.victor.myan.controller.TodayAnimeController
import com.victor.myan.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val todayAnimeController = TodayAnimeController()
    private val seasonAnimeController = SeasonAnimeController()

    companion object {
        fun newInstance(): HomeFragment {
            val homeFragment = HomeFragment()
            val args = Bundle()
            homeFragment.arguments = args
            return homeFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        todayAnimeController.getTodayAnime(view)
        seasonAnimeController.getSeasonAnime(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}