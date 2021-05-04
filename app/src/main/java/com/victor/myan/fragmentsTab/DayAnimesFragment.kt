package com.victor.myan.fragmentsTab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.myan.R
import com.victor.myan.databinding.FragmentDayAnimesBinding

class DayAnimesFragment : Fragment() {

    private lateinit var binding: FragmentDayAnimesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDayAnimesBinding.inflate(layoutInflater)
        return binding.root
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            DayAnimesFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}