package com.victor.myan.screens.animeDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.myan.R
import com.victor.myan.databinding.FragmentOverviewBinding
import com.victor.myan.screens.HomeFragment

class Overview : Fragment() {

    private lateinit var binding : FragmentOverviewBinding

    companion object {
        fun newInstance(mal_id : String): Overview {
            val overviewFragment = Overview()
            val args = Bundle()
            args.putString("mal_id", mal_id)
            overviewFragment.arguments = args
            return overviewFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOverviewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.text.text = arguments?.getString("mal_id")
    }
}