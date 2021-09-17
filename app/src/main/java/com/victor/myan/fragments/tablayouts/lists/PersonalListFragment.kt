package com.victor.myan.fragments.tablayouts.lists

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.myan.databinding.FragmentPersonalListBinding

class PersonalListFragment : Fragment() {

    private lateinit var binding : FragmentPersonalListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}