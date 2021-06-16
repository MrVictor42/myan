package com.victor.myan.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.myan.adapter.AnimeAdapter
import com.victor.myan.controller.CategoryByAiring
import com.victor.myan.controller.CategoryByCompleted
import com.victor.myan.controller.CategoryByUpcoming
import com.victor.myan.controller.CategoryController
import com.victor.myan.databinding.FragmentCategoryDetailBinding
import com.victor.myan.helper.AuxFunctionsHelper

class CategoryDetailFragment : Fragment() {

    private lateinit var binding : FragmentCategoryDetailBinding
    private val categoryController = CategoryController()
    private val categoryByAiring = CategoryByAiring()
    private val categoryByCompleted = CategoryByCompleted()
    private val categoryByUpcoming = CategoryByUpcoming()

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

        val typeText = binding.typeText

        typeText.text = type
        if (genreID != null) {
            categoryController.getCategoryByScore(view, genreID)
            categoryByAiring.getCategoryByAiring(view, genreID)
            categoryByCompleted.getCategoryByScore(view, genreID)
            categoryByUpcoming.getCategoryByUpcoming(view, genreID)
        }
    }
}