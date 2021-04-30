package com.victor.myan.fragmentsTab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.myan.R

class SeasonAnimeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_season_anime, container, false)
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            SeasonAnimeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}