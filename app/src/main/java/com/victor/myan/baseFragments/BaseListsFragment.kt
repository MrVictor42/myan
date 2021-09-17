package com.victor.myan.baseFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.myan.R
import com.victor.myan.databinding.FragmentBaseListsBinding
import com.victor.myan.fragments.GenreFragment

class BaseListsFragment : Fragment() {

    private lateinit var binding : FragmentBaseListsBinding

    companion object {
        fun newInstance(): BaseListsFragment {
            val listFragment = BaseListsFragment()
            val args = Bundle()
            listFragment.arguments = args
            return listFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseListsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}