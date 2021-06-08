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
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import com.victor.myan.R
import com.victor.myan.databinding.FragmentBottomSheetBinding
import com.victor.myan.services.impl.AuxServicesImpl

class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentBottomSheetBinding
    private val auxServicesImpl = AuxServicesImpl()

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

        val titleModal = view.findViewById<TextView>(R.id.title_modal)
        val imageModal = view.findViewById<ImageView>(R.id.image_modal)
        val yearModal = view.findViewById<TextView>(R.id.year_modal)
        val episodesModal = view.findViewById<TextView>(R.id.episode_modal)
        val textScoreModal = view.findViewById<TextView>(R.id.score_modal_text)
        val scoreModal = view.findViewById<TextView>(R.id.score_modal)
        val synopsisModal = view.findViewById<TextView>(R.id.synopsis_modal)

        Picasso.get().load(imageUrl).into(imageModal)
        titleModal.text = title

        if(airingStart != "" || airingStart != "null") {
            yearModal.text = airingStart.toString().substring(0,4)
        } else {
            yearModal.text = auxServicesImpl.getCurrentYear().toString()
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
    }
}