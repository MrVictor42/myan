package com.victor.myan.fragments.tablayouts.listsDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.myan.databinding.FragmentTopListBinding

class TopListFragment : Fragment() {

    private lateinit var binding : FragmentTopListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}