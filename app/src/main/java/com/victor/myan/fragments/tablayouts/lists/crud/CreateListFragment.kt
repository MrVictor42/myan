package com.victor.myan.fragments.tablayouts.lists.crud

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.myan.databinding.FragmentCreateListBinding

class CreateListFragment : Fragment() {

    private lateinit var binding : FragmentCreateListBinding

    companion object {
        fun newInstance(): CreateListFragment {
            val createListFragment = CreateListFragment()
            val args = Bundle()
            createListFragment.arguments = args
            return createListFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}