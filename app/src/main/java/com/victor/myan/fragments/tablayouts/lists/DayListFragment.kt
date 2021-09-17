package com.victor.myan.fragments.tablayouts.lists

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.myan.R
import com.victor.myan.databinding.FragmentDayListBinding

class DayListFragment : Fragment() {

    private lateinit var binding : FragmentDayListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDayListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}