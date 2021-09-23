package com.victor.myan.fragments.tablayouts.animeDetail

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.victor.myan.R
import com.victor.myan.databinding.FragmentOverviewAnimeBinding
import com.victor.myan.enums.StatusEnum
import com.victor.myan.fragments.dialogs.ListDialogFragment
import com.victor.myan.fragments.tablayouts.listsDetail.PersonalListFragment
import com.victor.myan.helper.AuxFunctionsHelper
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.helper.YoutubeHelper
import com.victor.myan.model.Anime
import com.victor.myan.model.PersonalList
import com.victor.myan.viewmodel.AnimeViewModel
import com.victor.myan.viewmodel.PersonalListViewModel

class OverviewAnimeFragment : Fragment() {

    private lateinit var binding : FragmentOverviewAnimeBinding
    private val auxServicesHelper = AuxFunctionsHelper()
    private val youtubeHelper = YoutubeHelper()
    private val TAG = OverviewAnimeFragment::class.java.simpleName
    private val animeViewModel by lazy {
        ViewModelProvider(this).get(AnimeViewModel::class.java)
    }
    private val personalListViewModel by lazy {
        ViewModelProvider(this).get(PersonalListViewModel::class.java)
    }

    companion object {
        fun newInstance(mal_id : Int): OverviewAnimeFragment {
            val overviewFragment = OverviewAnimeFragment()
            val args = Bundle()
            args.putInt("mal_id", mal_id)
            overviewFragment.arguments = args
            return overviewFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOverviewAnimeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getInt("mal_id")

        animeViewModel.getAnimeApi(malID!!)
        animeViewModel.anime.observe(viewLifecycleOwner, { state ->
            processAnimeResponse(state)
        })
    }

    @SuppressLint("InflateParams")
    private fun processAnimeResponse(state: ScreenStateHelper<Anime>?) {
        val btnAddList = binding.btnAddList
        val btnRemoveList = binding.btnRemoveList
        val progressBar = binding.progressBarOverview
        val animeTitle = binding.animeTitle
        val animeScore = binding.animeScore
        val animeImage = binding.animeImage
        val animePopularity = binding.animePopularity
        val animeFavorites = binding.animeFavorites
        val animeTitleSynonyms = binding.animeTitleSynonyms
        val animeStatus = binding.animeStatus
        val episodeDuration = binding.episodeDuration
        val typeYear = binding.typeYear
        var listGenres = ""
        var listLicensors = ""
        var listStudios = ""
        val animeVideo = binding.youtubePlayerView
        lifecycle.addObserver(animeVideo)
        val animeGenres = binding.animeGenres
        val animeLicensors = binding.animeLicensors
        val animeStudios = binding.animeStudios
        val expandableTextViewSynopsis = binding.expandableTextViewSynopsis.expandableTextView
        val expandableTextViewOpening = binding.expandableTextViewOpening.expandableTextView
        val expandableTextViewEnding = binding.expandableTextViewEnding.expandableTextView

        when(state) {
            is ScreenStateHelper.Loading -> {
                progressBar.visibility = View.VISIBLE
            }
            is ScreenStateHelper.Success -> {
                if(state.data != null) {
                    progressBar.visibility = View.GONE
                    with(state.data) {
                        animeTitle.text = title
                        Glide.with(view?.context!!).load(imageUrl).into(animeImage)

                        if(titleSynonyms.isNullOrEmpty() || titleSynonyms.equals("null")) {
                            animeTitleSynonyms.text = "─"
                        } else {
                            animeTitleSynonyms.text = titleSynonyms.toString()
                        }

                        if(popularity.toString().isNullOrEmpty() || popularity.toString() == "null") {
                            animePopularity.text = "─"
                        } else {
                            animePopularity.text = popularity.toString()
                        }

                        if(favorites.toString().isNullOrEmpty() || favorites.toString() == "null") {
                            animeFavorites.text = "─"
                        } else {
                            animeFavorites.text = favorites.toString()
                        }

                        val animeType = when(type) {
                            "" -> "─"
                            "null" -> "─"
                            else -> type
                        }

                        val year = if(premiered == "null" || premiered.isNullOrEmpty()) {
                            "─"
                        } else {
                            auxServicesHelper.formatPremiered(premiered)
                        }

                        val typeYearConcat = "$animeType, $year"
                        typeYear.text = typeYearConcat

                        if(score.toString().isNullOrEmpty() || score.toString() == "null") {
                            animeScore.text = "─"
                        } else {
                            animeScore.text = score.toString()
                        }

                        when (status) {
                            "null" -> animeStatus.text = "─"
                            "" -> animeStatus.text = "─"

                            StatusEnum.CurrentlyAiring.status -> {
                                animeStatus.text = StatusEnum.CurrentlyAiring.status
                                animeStatus.setTextColor(
                                    ContextCompat.getColor(requireContext(), R.color.green_light)
                                )
                            }

                            StatusEnum.NotYetAired.status -> {
                                animeStatus.text = StatusEnum.NotYetAired.status
                                animeStatus.setTextColor(
                                    ContextCompat.getColor(requireContext(), R.color.dark_blue)
                                )
                            }

                            StatusEnum.FinishedAiring.status -> {
                                animeStatus.text = StatusEnum.FinishedAiring.status
                                animeStatus.setTextColor(
                                    ContextCompat.getColor(requireContext(), R.color.red)
                                )
                            }
                        }

                        val episode = when (episodes.toString()) {
                            "null" -> "─"
                            "0" -> "─"
                            else -> episodes
                        }

                        val duration = when(duration) {
                            "null" -> "─"
                            "0" -> "─"
                            "" -> "─"
                            else -> auxServicesHelper.formatDurationEpisode(animeType, duration)
                        }

                        val epiDuration = "$episode eps, $duration"
                        episodeDuration.text = epiDuration

                        if (genreList.isEmpty()) {
                            // Nothing to do
                        } else {
                            for (genre in genreList.indices) {

                                listGenres += genreList[genre].name
                                if (genre < genreList.size - 1) {
                                    listGenres += " • "
                                }
                            }
                            animeGenres.text = listGenres
                        }

                        if (licensorList.isEmpty()) {
                            animeLicensors.text = getString(R.string.unknown)
                        } else {
                            for (licensor in licensorList.indices) {
                                listLicensors += licensorList[licensor].name
                                if (licensor < licensorList.size - 1) {
                                    listLicensors += "\n"
                                }
                            }
                            animeLicensors.text = listLicensors
                        }

                        if (studioList.isEmpty()) {
                            animeStudios.text = getString(R.string.unknown)
                        } else {
                            for (studio in studioList.indices) {
                                listStudios += studioList[studio].name
                                if (studio < studioList.size - 1) {
                                    listStudios += "\n"
                                }
                            }
                            animeStudios.text = listStudios
                        }

                        if (trailerUrl.isNullOrEmpty() || trailerUrl == "null") {
                            Toast.makeText(
                                context,
                                auxServicesHelper.capitalize(
                                    "this anime doesn't have a preview yet"
                                ), Toast.LENGTH_LONG
                            ).show()
                        } else {
                            animeVideo.visibility = View.VISIBLE
                            animeVideo.addYouTubePlayerListener(object :
                                AbstractYouTubePlayerListener() {
                                override fun onReady(youTubePlayer: YouTubePlayer) {
                                    val videoId =
                                        youtubeHelper.extractVideoIdFromUrl(trailerUrl)
                                            .toString()
                                    youTubePlayer.loadVideo(videoId, 0f)
                                    youTubePlayer.pause()
                                }
                            })
                        }

                        expandableTextViewSynopsis.text = synopsis
                        expandableTextViewOpening.text = openingThemes.toString().replace(",", "\n")
                            .replace("[", "").replace("]", "")
                        expandableTextViewEnding.text = endingThemes.toString().replace(",", "\n")
                            .replace("[", "").replace("]", "")
                    }

                    when(personalListViewModel.existsList()) {
                        true -> {
                            btnRemoveList.visibility = View.VISIBLE
                        }
                        false -> {
                            btnAddList.visibility = View.VISIBLE

                            btnAddList.setOnClickListener {
                                ListDialogFragment().show(childFragmentManager, TAG)
                            }
                        }
                    }
                }
            }
            is ScreenStateHelper.Error -> {

            }
            else -> {
                //"Nothing to do"
            }
        }
    }
}