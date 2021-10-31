package com.victor.myan.fragments.tablayouts.listsDetail.season

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.victor.myan.R
import com.victor.myan.databinding.FragmentSeasonBinding
import com.victor.myan.fragments.tablayouts.listsDetail.day.DayFragment

class SeasonFragment : Fragment() {

    private lateinit var binding : FragmentSeasonBinding

    companion object {
        fun newInstance(season : String): SeasonFragment {
            val seasonFragment = SeasonFragment()
            val args = Bundle()
            args.putString("season", season)
            seasonFragment.arguments = args
            return seasonFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSeasonBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val season = arguments?.getString("season")
        Toast.makeText(context, season, Toast.LENGTH_SHORT).show()
    }
}