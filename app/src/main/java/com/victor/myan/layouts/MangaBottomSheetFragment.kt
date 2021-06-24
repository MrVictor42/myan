package com.victor.myan.layouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import com.victor.myan.R
import com.victor.myan.databinding.FragmentMangaBottomSheetBinding
import com.victor.myan.messages.Messages
import com.victor.myan.fragments.MangaDetailFragment
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
        var year : String = ""

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
            } else -> yearModal.text = auxFunctionsHelper.capitalize(Messages.Undefined.message)
        }

        if(volumes != 0) {
            volumesModal.text = volumes.toString()
        } else {
            volumesModal.text = auxFunctionsHelper.capitalize(Messages.Undefined.message)
        }

        if(score != 0.0) {
            scoreModal.text = score.toString()
        } else {
            scoreModal.text = auxFunctionsHelper.capitalize(Messages.Undefined.message)
        }

        btnMoreInformations.setOnClickListener {
            val fragment = MangaDetailFragment()
            val fragmentManager = fragmentManager

            val bundle = Bundle()
            bundle.putString("mal_id", malID)
            bundle.putString("year", year)

            fragment.arguments = bundle

            val transaction = fragmentManager?.beginTransaction()?.replace(R.id.content, fragment)
            transaction?.commit()
            getFragmentManager()?.beginTransaction()?.remove(this)?.commit()
        }
    }
}