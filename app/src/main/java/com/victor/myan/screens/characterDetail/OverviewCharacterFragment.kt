package com.victor.myan.screens.characterDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.myan.R
import com.victor.myan.databinding.FragmentOverviewCharacterBinding
import com.victor.myan.screens.animeDetail.OverviewFragment

class OverviewCharacterFragment : Fragment() {

    private lateinit var binding : FragmentOverviewCharacterBinding

    companion object {
        fun newInstance(mal_id : String): OverviewCharacterFragment {
            val overviewFragment = OverviewCharacterFragment()
            val args = Bundle()
            args.putString("mal_id", mal_id)
            overviewFragment.arguments = args
            return overviewFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentOverviewCharacterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}