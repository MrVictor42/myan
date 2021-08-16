package com.victor.myan.screens.animeDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.myan.databinding.FragmentStaffBinding

class Staff : Fragment() {

    private lateinit var binding : FragmentStaffBinding

    companion object {
        fun newInstance(mal_id : String): Staff {
            val staffFragment = Staff()
            val args = Bundle()
            args.putString("mal_id", mal_id)
            staffFragment.arguments = args
            return staffFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStaffBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}