package com.victor.myan.layouts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import com.victor.myan.R
import com.victor.myan.databinding.FragmentBottomSheetBinding
import com.victor.myan.fragments.AnimeDetailFragment
import com.victor.myan.services.impl.AuxServicesImpl

class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getString("mal_id")
        val imageUrl = arguments?.getString("image_url")
        val airingStart = arguments?.getString("airing_start")
        val title = arguments?.getString("title")
        val episodes = arguments?.getInt("episodes")
        val score = arguments?.getDouble("score")
        val synopsis = arguments?.getString("synopsis")
        val startDate = arguments?.getString("start_date")

        val titleModal = binding.titleModal
        val imageModal = binding.imageModal
        val yearModal = binding.yearModal
        val episodesModal = binding.episodeModal
        val textScoreModal = binding.scoreModalText
        val scoreModal = binding.scoreModal
        val synopsisModal = binding.synopsisModal
        val btnMoreInformations = binding.btnMoreInformationModal

        Picasso.get().load(imageUrl).into(imageModal)
        titleModal.text = title

        if(airingStart != "" && airingStart != "null") {
            yearModal.text = airingStart.toString().substring(0,4)
        } else if(startDate != "null") {
            yearModal.text = startDate.toString().substring(4,8)
        } else {
            yearModal.isInvisible = true
        }

        if(episodes == 0) {
            episodesModal.isInvisible = true
            episodesModal.isInvisible = true
        } else {
            episodesModal.text = episodes.toString()
        }

        if(score == 0.0) {
            textScoreModal.isInvisible = true
            scoreModal.isInvisible = true
        } else {
            scoreModal.text = score.toString()
        }

        if(synopsis != "null") {
            synopsisModal.text = synopsis
        } else {
            synopsisModal.isInvisible = true
        }

        btnMoreInformations.setOnClickListener {
            val fragment = AnimeDetailFragment()
            val fragmentManager = fragmentManager

            val bundle = Bundle()
            bundle.putString("mal_id", malID)

            fragment.arguments = bundle

            val transaction = fragmentManager?.beginTransaction()?.replace(R.id.content, fragment)
            transaction?.commit()
            getFragmentManager()?.beginTransaction()?.remove(this)?.commit()

        }
    }
}