package com.victor.myan.screens.personDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.myan.databinding.FragmentOverviewPersonBinding

class OverviewPersonFragment : Fragment() {

    private lateinit var binding : FragmentOverviewPersonBinding

    companion object {
        fun newInstance(mal_id : String): OverviewPersonFragment {
            val overviewFragment = OverviewPersonFragment()
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
        binding = FragmentOverviewPersonBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}