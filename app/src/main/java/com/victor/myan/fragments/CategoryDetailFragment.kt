package com.victor.myan.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.myan.databinding.FragmentCategoryDetailBinding

class CategoryDetailFragment : Fragment() {

    private lateinit var binding : FragmentCategoryDetailBinding

    companion object {
        fun newInstance(): CategoryDetailFragment {
            val categoryFragment = CategoryDetailFragment()
            val args = Bundle()
            categoryFragment.arguments = args
            return categoryFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val genreID = arguments?.getInt("genre")
        val type = arguments?.getString("type")
    }
}