package com.victor.myan.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.victor.myan.adapter.ViewPagerAnimeSlideAdapter
import com.victor.myan.api.CategoryApi
import com.victor.myan.controller.*
import com.victor.myan.databinding.FragmentHomeBinding
import com.victor.myan.enums.TypesRequest
import com.victor.myan.helper.JikanApiInstanceHelper
import com.victor.myan.model.Anime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewPagerAnimeSlideController = ViewPagerAnimeSlideController()
    private val todayAnimeController = TodayAnimeController()
    private val seasonAnimeController = SeasonController()
    private val topAnimeController = TopAnimeController()
    private val mangaTopController = MangaTopController()

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
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}