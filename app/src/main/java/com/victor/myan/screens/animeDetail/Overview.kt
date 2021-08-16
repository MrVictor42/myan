package com.victor.myan.screens.animeDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.squareup.picasso.Picasso
import com.victor.myan.R
import com.victor.myan.api.AnimeApi
import com.victor.myan.databinding.FragmentOverviewBinding
import com.victor.myan.enums.StatusEnum
import com.victor.myan.helper.AuxFunctionsHelper
import com.victor.myan.helper.JikanApiInstanceHelper
import com.victor.myan.helper.YoutubeHelper
import com.victor.myan.model.Anime
import com.victor.myan.screens.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Intent
import android.net.Uri


class Overview : Fragment() {

    private lateinit var binding : FragmentOverviewBinding
    private val auxServicesHelper = AuxFunctionsHelper()
    private val youtubeHelper = YoutubeHelper()

    companion object {
        fun newInstance(mal_id : String, year : String): Overview {
            val overviewFragment = Overview()
            val args = Bundle()
            args.putString("mal_id", mal_id)
            args.putString("year", year)
            overviewFragment.arguments = args
            return overviewFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOverviewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getString("mal_id").toString()
        val year = arguments?.getString("year").toString()
        var listGenres = ""
        var listLicensors = ""
        var listStudios = ""
        val animeVideo = binding.youtubePlayerView
        lifecycle.addObserver(animeVideo)
        val animeTitle = binding.animeTitle
        val animeTitleSynonyms = binding.animeTitleSynonyms
        val animeStatus = binding.animeStatus
        val animeScore = binding.animeScore
        val animeImage = binding.animeImage
        val animeGenres = binding.animeGenres
        val animeLicensors = binding.animeLicensors
        val animeStudios = binding.animeStudios
        val episodeDuration = binding.episodeDuration
        val animePopularity = binding.animePopularity
        val animeMembers = binding.animeMembers
        val animeFavorites = binding.animeFavorites
        val expandableTextViewSynopsis = binding.expandableTextViewSynopsis.expandableTextView
        val expandableTextViewOpening = binding.expandableTextViewOpening.expandableTextView
        val expandableTextViewEnding = binding.expandableTextViewEnding.expandableTextView
        val typeYear = binding.typeYear
        val animeApi = JikanApiInstanceHelper.getJikanApiInstance().create(AnimeApi::class.java)

        animeApi.getAnime(malID).enqueue(object : Callback<Anime> {
            override fun onFailure(call: Call<Anime>, t: Throwable) {

            }

            override fun onResponse(call: Call<Anime>, response: Response<Anime>) {
                if (response.isSuccessful) {
                    val animeResponse = response.body()
                    if (animeResponse != null) {
                        Picasso.get().load(animeResponse.image_url)
                            .placeholder(R.drawable.placeholder).fit().into(animeImage)

                        animeTitle.text = animeResponse.title
                        animePopularity.text = animeResponse.popularity.toString()
                        animeMembers.text = animeResponse.members.toString()
                        animeFavorites.text = animeResponse.favorites.toString()

                        if (animeResponse.title_synonyms.isEmpty()) {
                            animeTitleSynonyms.text = "─"
                        } else {
                            animeTitleSynonyms.text = animeResponse.title_synonyms.toString()
                        }

                        val type = when (animeResponse.type) {
                            "" -> "─"
                            "null" -> "─"
                            else -> animeResponse.type
                        }

                        val typeYearConcat = "$type, $year"
                        typeYear.text = typeYearConcat

                        val episode = when (animeResponse.episodes.toString()) {
                            "null" -> "─"
                            "0" -> "─"
                            else -> animeResponse.episodes
                        }

                        val duration =
                            auxServicesHelper.formatDurationEpisode(type, animeResponse.duration)
                        val epiDuration = "$episode eps, $duration"

                        episodeDuration.text = epiDuration

                        when (animeResponse.status) {
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

                        if (animeResponse.score.toString()
                                .isNullOrEmpty() || animeResponse.score == 0.0
                        ) {
                            animeScore.text = "─"
                        } else {
                            animeScore.text = animeResponse.score.toString()
                        }

                        if (animeResponse.genres.isEmpty()) {
                            // Nothing to do
                        } else {
                            for (genre in animeResponse.genres.indices) {

                                listGenres += animeResponse.genres[genre].name
                                if (genre < animeResponse.genres.size - 1) {
                                    listGenres += " • "
                                }
                            }
                            animeGenres.text = listGenres
                        }

                        if (animeResponse.licensors.isEmpty()) {
                            animeLicensors.text = "Unknown"
                        } else {
                            for (licensor in animeResponse.licensors.indices) {
                                listLicensors += animeResponse.licensors[licensor].name
                                if (licensor < animeResponse.licensors.size - 1) {
                                    listLicensors += "\n"
                                }
                            }
                            animeLicensors.text = listLicensors
                        }

                        if (animeResponse.studios.isEmpty()) {
                            animeStudios.text = "Unknown"
                        } else {
                            for (studio in animeResponse.studios.indices) {
                                listStudios += animeResponse.studios[studio].name
                                if (studio < animeResponse.studios.size - 1) {
                                    listStudios += "\n"
                                }
                            }
                            animeStudios.text = listStudios
                        }

                        animeVideo.addYouTubePlayerListener(object :
                            AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                if (animeResponse.trailer_url.isNullOrEmpty()) {
                                    Toast.makeText(
                                        context,
                                        auxServicesHelper.capitalize(
                                            "this anime doesn't have a preview yet"
                                        ), Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    val videoId =
                                        youtubeHelper.extractVideoIdFromUrl(animeResponse.trailer_url)
                                            .toString()
                                    youTubePlayer.loadVideo(videoId, 0f)
                                    youTubePlayer.pause()
                                }
                            }
                        })

                        expandableTextViewSynopsis.text = animeResponse.synopsis
                        expandableTextViewOpening.text =
                            animeResponse.opening_themes.toString().replace(",", "\n")
                                .replace("[", "").replace("]", "")
                        expandableTextViewEnding.text =
                            animeResponse.ending_themes.toString().replace(",", "\n")
                                .replace("[", "").replace("]", "")
                    }
                }
            }
        })
    }
}