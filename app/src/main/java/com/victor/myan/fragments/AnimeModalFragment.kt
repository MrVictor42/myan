package com.victor.myan.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isInvisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import com.victor.myan.R
import com.victor.myan.databinding.FragmentModalBinding

class AnimeModalFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentModalBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentModalBinding.inflate(layoutInflater, container, false)
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

        val imageModal = view.findViewById<ImageView>(R.id.image_modal)
        val titleModal = view.findViewById<TextView>(R.id.text_modal)
        val yearModal = view.findViewById<TextView>(R.id.year_modal)
        val textEpisodesModal = view.findViewById<TextView>(R.id.text_episode_modal)
        val episodesModal = view.findViewById<TextView>(R.id.episodes_modal)
        val textScoreModal = view.findViewById<TextView>(R.id.text_score_modal)
        val scoreModal = view.findViewById<TextView>(R.id.score_modal)
        val synopsisModal = view.findViewById<TextView>(R.id.synopsis_modal)
        val btnInformation = view.findViewById<Button>(R.id.more_informations_modal)

        Picasso.get().load(imageUrl).into(imageModal)
        titleModal.text = title
        yearModal.text = airingStart
        synopsisModal.text = synopsis

        if(episodes == 0) {
            textEpisodesModal.isInvisible = true
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

        btnInformation.setOnClickListener {
            val fragment = AnimeDetailFragment()
            val fragmentManager = fragmentManager

            val bundle = Bundle()
            bundle.putString("mal_id", malID)
            bundle.putString("airing_start", airingStart)

            fragment.arguments = bundle

            val transaction = fragmentManager?.beginTransaction()?.replace(R.id.content, fragment)
            transaction?.commit()
            getFragmentManager()?.beginTransaction()?.remove(this)?.commit()
        }
    }
}