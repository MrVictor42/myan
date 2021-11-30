package com.victor.myan.fragments.tablayouts.anime

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.victor.myan.R
import com.victor.myan.adapter.AdaptationItemAdapter
import com.victor.myan.adapter.GenreItemAdapter
import com.victor.myan.databinding.FragmentOverviewAnimeBinding
import com.victor.myan.enums.StatusEnum
import com.victor.myan.helper.AuxFunctionsHelper
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.helper.YoutubeHelper
import com.victor.myan.model.Anime
import com.victor.myan.viewmodel.AnimeViewModel
import com.victor.myan.fragments.tablayouts.lists.personalList.ListDialogFragment
import com.victor.myan.model.Adaptation
import com.victor.myan.model.Genre

class OverviewAnimeFragment : Fragment() {

    private lateinit var binding : FragmentOverviewAnimeBinding
    private lateinit var genreItemAdapter: GenreItemAdapter
    private lateinit var adaptationItemAdapter: AdaptationItemAdapter
    private val animeViewModel by lazy {
        ViewModelProvider(this)[AnimeViewModel::class.java]
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
        val malID = arguments?.getInt("mal_id")!!
        val auxServicesHelper = AuxFunctionsHelper()
        val youtubeHelper = YoutubeHelper()

        val animeImage = binding.animeImage
        val animeRank = binding.animeRank
        val animeScore = binding.animeScore
        val btnAddList = binding.btnAddList
        val animeTitle = binding.animeTitle
        val animeTitleSynonyms = binding.animeTitleSynonyms
        val animeStatus = binding.animeStatus
        val episodeDuration = binding.episodeDuration
        val typeYear = binding.typeYear
        val listGenres : MutableList<Genre> = arrayListOf()
        var listLicensors = ""
        var listStudios = ""
        val animeVideo = binding.youtubePlayerView
        lifecycle.addObserver(animeVideo)
        val animeGenres = binding.recyclerViewGenres
        val animeLicensors = binding.animeLicensors
        val animeStudios = binding.animeStudios
        val openingThemeText = binding.openingThemeTextView
        val endingThemeText = binding.endingThemeTextView
        val expandableTextViewSynopsis = binding.expandableTextViewSynopsis.expandableTextView
        val expandableTextViewOpening = binding.expandableTextViewOpening.expandableTextView
        val expandableTextViewEnding = binding.expandableTextViewEnding.expandableTextView
        val adaptationsText = binding.animeAdaptationText
        val animeAdaptations = binding.recyclerViewAdaptations
        val listAdaptations: MutableList<Adaptation> = mutableListOf()
        val overviewAnime = binding.overviewAnime
        val shimmerLayout = binding.shimmerLayout

        animeViewModel.getAnimeApi(malID)
        animeViewModel.anime.observe(viewLifecycleOwner, { anime ->
            when(anime) {
                is ScreenStateHelper.Loading -> {
                    shimmerLayout.startShimmer()
                }
                is ScreenStateHelper.Success -> {
                    if(anime.data != null) {
                        with(anime.data) {
                            animeTitle.text = title
                            Glide.with(view.context!!).load(imageURL).into(animeImage)

                            if(rank.toString().isNullOrEmpty() || rank.toString() == "null") {
                                animeRank.text = "─"
                            } else {
                                animeRank.text = rank.toString()
                            }

                            if(score.toString().isNullOrEmpty() || score.toString() == "null") {
                                animeScore.text = "─"
                            } else {
                                animeScore.text = score.toString()
                            }

                            if(titleSynonyms.isNullOrEmpty() || titleSynonyms.equals("null")) {
                                animeTitleSynonyms.text = "─"
                            } else {
                                animeTitleSynonyms.text = titleSynonyms.toString()
                            }

                            val animeType = if(type.isNullOrEmpty() || type == "null") {
                                "─"
                            } else {
                                type
                            }

                            val year = if(premiered.isNullOrEmpty() || premiered == "null") {
                                "─"
                            } else {
                                auxServicesHelper.formatPremiered(premiered)
                            }

                            val typeYearConcat = "$animeType, $year"
                            typeYear.text = typeYearConcat


                            if(status.isNullOrEmpty() || status == "null") {
                                animeStatus.text = "─"
                            } else {
                                when(status) {
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
                            }

                            val episode =
                                if(episodes.toString().isNullOrEmpty()  ||
                                    episodes.toString() == "0" ||
                                    episodes.toString() == "null") {
                                    "─"
                                } else {
                                    episodes
                                }

                            val duration =
                                if(duration.isNullOrEmpty() ||
                                    duration == "0" ||
                                    duration == "null") {
                                    "─"
                                } else {
                                    auxServicesHelper.formatDurationEpisode(animeType, duration)
                                }

                            val epiDuration = "$episode eps, $duration"
                            episodeDuration.text = epiDuration

                            if (genreList.isEmpty()) {
                                // Nothing to do
                            } else {
                                for(aux in genreList.indices) {
                                    val genre = Genre()

                                    genre.name = genreList[aux].name
                                    genre.malID = genreList[aux].malID
                                    listGenres.add(genre)
                                }
                                genreItemAdapter = GenreItemAdapter(listGenres)
                                animeGenres.layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
                                animeGenres.adapter = genreItemAdapter
                            }

                            if(related.toString().isEmpty() || related.toString() == "null") {
                                // Nothing to do
                            } else {
                                if(related!!.adaptations.isEmpty()) {
                                    adaptationsText.visibility = View.GONE
                                } else {
                                    for(aux in related!!.adaptations.indices) {
                                        val adaptation = Adaptation()

                                        adaptation.name = related!!.adaptations[aux].name
                                        adaptation.type = related!!.adaptations[aux].type
                                        adaptation.malID = related!!.adaptations[aux].malID
                                        listAdaptations.add(adaptation)
                                    }
                                    adaptationItemAdapter = AdaptationItemAdapter(listAdaptations)
                                    animeAdaptations.layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
                                    animeAdaptations.adapter = adaptationItemAdapter
                                }
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
                                // Nothing to do
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

                            if(openingList.toString().isNullOrEmpty() || openingList.toString() == "null" || openingList.isEmpty()) {
                                openingThemeText.visibility = View.GONE
                            } else {
                                expandableTextViewOpening.text =
                                    openingList.toString().replace(",", "\n")
                                        .replace("[", "").replace("]", "")
                            }

                            if(endingList.toString().isNullOrEmpty() || endingList.toString() == "null" || endingList.isEmpty()) {
                                endingThemeText.visibility = View.GONE
                            } else {
                                expandableTextViewEnding.text =
                                    endingList.toString().replace(",", "\n")
                                        .replace("[", "").replace("]", "")
                            }

                            btnAddList.setOnClickListener {
                                ListDialogFragment(anime.data, null).show(childFragmentManager, "OverViewAnimeFragment")
                            }

                            shimmerLayout.stopShimmer()
                            shimmerLayout.visibility = View.GONE
                            overviewAnime.visibility = View.VISIBLE
                        }
                    }
                }
                else -> {

                }
            }
        })
    }
}