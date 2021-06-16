package com.victor.myan.layouts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import com.victor.myan.R
import com.victor.myan.databinding.FragmentAnimeBottomSheetBinding
import com.victor.myan.enums.MessagesEnum
import com.victor.myan.fragments.AnimeDetailFragment
import com.victor.myan.helper.AuxFunctionsHelper

class AnimeBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentAnimeBottomSheetBinding
    private val auxFunctionsHelper = AuxFunctionsHelper()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimeBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getString("mal_id")
        val imageUrl = arguments?.getString("image_url")
        val airingStart = arguments?.getString("airing_start")
        val title = arguments?.getString("title")
        val episodes = arguments?.getInt("episodes")
        val score = arguments?.getDouble("score")
        var year : String = ""

        val titleModal = binding.titleModal
        val imageModal = binding.imageModal
        val yearModal = binding.yearModal
        val episodesModal = binding.episodeModal
        val textScoreModal = binding.scoreModalText
        val scoreModal = binding.scoreModal
        val btnMoreInformations = binding.btnMoreInformationModal

        Picasso.get().load(imageUrl).into(imageModal)
        titleModal.text = title

        when {
            airingStart != null && !airingStart.toString().isEmpty() -> {
                yearModal.text = auxFunctionsHelper.formatYear(airingStart)
                year = auxFunctionsHelper.formatYear(airingStart)
            } else -> yearModal.text = auxFunctionsHelper.capitalize(MessagesEnum.Undefined.message)
        }

        if(episodes == 0) {
            episodesModal.text = auxFunctionsHelper.capitalize(MessagesEnum.Undefined.message)
        } else {
            episodesModal.text = episodes.toString()
        }

        if(score == 0.0) {
            textScoreModal.isInvisible = true
            scoreModal.isInvisible = true
        } else {
            scoreModal.text = score.toString()
        }

        btnMoreInformations.setOnClickListener {
            val fragment = AnimeDetailFragment()
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