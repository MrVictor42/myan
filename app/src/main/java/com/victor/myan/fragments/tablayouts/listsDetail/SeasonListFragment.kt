package com.victor.myan.fragments.tablayouts.listsDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.myan.databinding.FragmentSeasonListBinding

class SeasonListFragment : Fragment() {

    private lateinit var binding : FragmentSeasonListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSeasonListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}