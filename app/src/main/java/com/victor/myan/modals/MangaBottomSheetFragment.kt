package com.victor.myan.modals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import com.victor.myan.R
import com.victor.myan.databinding.FragmentMangaBottomSheetBinding
import com.victor.myan.screens.MangaDetailFragment
import com.victor.myan.helper.AuxFunctionsHelper

class MangaBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentMangaBottomSheetBinding
    private val auxFunctionsHelper = AuxFunctionsHelper()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMangaBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val malID = arguments?.getString("mal_id")
        val imageUrl = arguments?.getString("image_url")
        val title = arguments?.getString("title")
        val volumes = arguments?.getInt("volumes")
        val score = arguments?.getDouble("score")
        val startDate = arguments?.getString("start_date")
        var year = ""

        val titleModal = binding.titleModal
        val imageModal = binding.imageModal
        val volumesModal = binding.volumesModal
        val scoreModal = binding.scoreModal
        val yearModal = binding.yearModal
        val btnMoreInformations = binding.btnMoreInformationModal

        Picasso.get().load(imageUrl).into(imageModal)
        titleModal.text = title

        when {
            startDate != null -> {
                yearModal.text = auxFunctionsHelper.formatYear(startDate)
                year = auxFunctionsHelper.formatYear(startDate)
            } else -> yearModal.text = "─"
        }

        when(volumes) {
            0 -> volumesModal.text = "─"
            else -> volumesModal.text = volumes.toString()
        }

        when(score) {
            0.0 -> scoreModal.text = "─"
            else -> scoreModal.text = score.toString()
        }

        btnMoreInformations.setOnClickListener {
            val fragment = MangaDetailFragment()
            val fragmentManager = activity?.supportFragmentManager

            val bundle = Bundle()
            bundle.putString("mal_id", malID)
            bundle.putString("year", year)

            fragment.arguments = bundle

            val transaction = fragmentManager?.beginTransaction()?.replace(R.id.content, fragment)
            transaction?.commit()
            fragmentManager?.beginTransaction()?.remove(this)?.commit()
        }
    }
}