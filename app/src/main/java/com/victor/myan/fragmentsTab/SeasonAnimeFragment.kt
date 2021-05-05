package com.victor.myan.fragmentsTab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.myan.R

class SeasonAnimeFragment : Fragment() {

    companion object {
        fun newInstance(): SeasonAnimeFragment {
            val seasonAnimeFragment = SeasonAnimeFragment()
            val args = Bundle()
            seasonAnimeFragment.arguments = args
            return seasonAnimeFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_season_anime, container, false)
    }
}