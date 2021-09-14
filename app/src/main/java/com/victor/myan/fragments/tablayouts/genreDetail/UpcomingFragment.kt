package com.victor.myan.fragments.tablayouts.genreDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.myan.R
import com.victor.myan.databinding.FragmentUpcomingBinding

class UpcomingFragment : Fragment() {

    private lateinit var binding : FragmentUpcomingBinding

    companion object {
        fun newInstance(mal_id : Int, type : String): UpcomingFragment {
            val upcomingFragment = UpcomingFragment()
            val args = Bundle()
            args.putInt("mal_id", mal_id)
            args.putString("type", type)
            upcomingFragment.arguments = args
            return upcomingFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpcomingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}