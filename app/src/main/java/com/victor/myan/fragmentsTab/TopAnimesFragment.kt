package com.victor.myan.fragmentsTab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.myan.R

class TopAnimesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_animes, container, false)
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            TopAnimesFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}