package com.victor.myan.fragmentsTab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.myan.R
import com.victor.myan.databinding.FragmentDayAnimesBinding
import com.victor.myan.fragments.HomeFragment

class DayAnimesFragment : Fragment() {

    private lateinit var binding: FragmentDayAnimesBinding

    companion object {
        fun newInstance(): DayAnimesFragment {
            val dayAnimesFragment = DayAnimesFragment()
            val args = Bundle()
            dayAnimesFragment.arguments = args
            return dayAnimesFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDayAnimesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}