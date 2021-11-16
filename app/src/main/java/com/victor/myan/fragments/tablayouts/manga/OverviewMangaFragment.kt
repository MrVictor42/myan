package com.victor.myan.fragments.tablayouts.manga

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.victor.myan.R
import com.victor.myan.databinding.FragmentOverviewMangaBinding
import com.victor.myan.enums.MangaStatusEnum
import com.victor.myan.enums.StatusEnum
import com.victor.myan.helper.AuxFunctionsHelper
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.viewmodel.MangaViewModel

class OverviewMangaFragment : Fragment() {

    private lateinit var binding : FragmentOverviewMangaBinding
    private val mangaViewModel by lazy {
        ViewModelProvider(this)[MangaViewModel::class.java]
    }

    companion object {
        fun newInstance(mal_id : Int): OverviewMangaFragment {
            val overviewFragment = OverviewMangaFragment()
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
        binding = FragmentOverviewMangaBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val auxServicesHelper = AuxFunctionsHelper()
        val malID = arguments?.getInt("mal_id")!!
        val mangaImage = binding.image
        val mangaRank = binding.mangaRank
        val mangaScore = binding.mangaScore
        val mangaAuthors = binding.mangaAuthor
        val mangaTitle = binding.mangaTitle
        val mangaTitleSynonyms = binding.mangaTitleSynonyms
        val mangaStatus = binding.mangaStatus
        val mangaGenres = binding.mangaGenres
        val volumesChapter = binding.volumesChapters
        val mangaAdaptations = binding.mangaAdaptations
        val mangaSpinOff = binding.mangaSpinoff
        val typeYear = binding.typeYear
        val expandableTextViewSynopsis = binding.expandableTextViewSynopsis.expandableTextView
        val expandableTextViewBackground = binding.expandableTextViewBackground.expandableTextView
        val listGenres: MutableList<String> = mutableListOf()
        val listAuthors: MutableList<String> = mutableListOf()
        val listAdaptations: MutableList<String> = mutableListOf()
        val listSpinOff: MutableList<String> = mutableListOf()
        val shimmerLayout = binding.shimmerLayout
        val overviewManga = binding.overviewManga

        mangaViewModel.getManga(malID)
        mangaViewModel.manga.observe(viewLifecycleOwner, { manga ->
            when(manga) {
                is ScreenStateHelper.Loading -> {
                    shimmerLayout.startShimmer()
                }
                is ScreenStateHelper.Success -> {
                    if(manga.data != null) {
                        with(manga.data) {
                            mangaTitle.text = title
                            Glide.with(view.context!!).load(imageURL).into(mangaImage)

                            if(titleSynonyms.isNullOrEmpty() || titleSynonyms.equals("null")) {
                                mangaTitleSynonyms.text = "─"
                            } else {
                                mangaTitleSynonyms.text = titleSynonyms.toString()
                            }

                            if(rank.toString().isNullOrEmpty() || rank.toString() == "null") {
                                mangaRank.text = "─"
                            } else {
                                mangaRank.text = rank.toString()
                            }

                            if(score.toString().isNullOrEmpty() || score.toString() == "null") {
                                mangaScore.text = "─"
                            } else {
                                mangaScore.text = score.toString()
                            }

                            if(status.isNullOrEmpty() || status == "null") {
                                mangaStatus.text = "─"
                            } else {
                                when(status) {
                                    StatusEnum.CurrentlyAiring.status -> {
                                        mangaStatus.text = StatusEnum.CurrentlyAiring.status
                                        mangaStatus.setTextColor(
                                            ContextCompat.getColor(requireContext(), R.color.green_light)
                                        )
                                    }

                                    StatusEnum.NotYetAired.status -> {
                                        mangaStatus.text = StatusEnum.NotYetAired.status
                                        mangaStatus.setTextColor(
                                            ContextCompat.getColor(requireContext(), R.color.dark_blue)
                                        )
                                    }

                                    StatusEnum.FinishedAiring.status -> {
                                        mangaStatus.text = StatusEnum.FinishedAiring.status
                                        mangaStatus.setTextColor(
                                            ContextCompat.getColor(requireContext(), R.color.red)
                                        )
                                    }

                                    MangaStatusEnum.Publishing.name -> {
                                        mangaStatus.text = MangaStatusEnum.Publishing.name
                                        mangaStatus.setTextColor(
                                            ContextCompat.getColor(
                                                requireContext(),
                                                R.color.green_light
                                            )
                                        )
                                    }
                                    MangaStatusEnum.Finished.name -> {
                                        mangaStatus.text = MangaStatusEnum.Finished.name
                                        mangaStatus.setTextColor(
                                            ContextCompat.getColor(
                                                requireContext(),
                                                R.color.red
                                            )
                                        )
                                    }
                                }
                            }

                            val mangaVolumes = if(volumes.toString().isEmpty() || volumes.toString() == "null" || volumes.toString() == "0") {
                                "─"
                            } else {
                                volumes
                            }

                            val mangaChapters = if(chapters.toString().isEmpty() || chapters.toString() == "null" || chapters.toString() == "0") {
                                "─"
                            } else {
                                chapters
                            }

                            volumesChapter.text = "$mangaVolumes vol, $mangaChapters ch"

                            val mangaType = if(type.isNullOrEmpty() || type == "null") {
                                "─"
                            } else {
                                type
                            }

                            val year = if(published!!.year.isNullOrEmpty() || published!!.year == "") {
                                "─"
                            } else {
                                published!!.year.substring(0,4)
                            }

                            typeYear.text = "$mangaType, $year"

                            if (authors.toString().isEmpty() || authors.toString() == "null") {
                                mangaAuthors.text = auxServicesHelper.capitalize("not was found author from this manga")
                            } else {
                                for(aux in authors.indices) {
                                    listAuthors.add(authors[aux].name)
                                }
                                mangaAuthors.text = listAuthors.toString()
                                listAuthors.clear()
                            }

                            if(genres.toString().isEmpty() || genres.toString() == "null") {
                                mangaGenres.text = auxServicesHelper.capitalize("not found the genres")
                            } else {
                                for(aux in genres.indices) {
                                    listGenres.add(genres[aux].name)
                                }
                                mangaGenres.text = listGenres.toString()
                                listGenres.clear()
                            }

                            expandableTextViewSynopsis.text = synopsis
                            expandableTextViewBackground.text = background

                            if(related.toString().isEmpty() || related.toString() == "null") {
                                // Da uns gone pra spin off e adaptations
                            } else {
                                for(aux in related!!.adaptations.indices) {
                                    listAdaptations.add(related!!.adaptations[aux].name)
                                }
                                mangaAdaptations.text = listAdaptations.toString()
                                listAdaptations.clear()
                                for(aux in related!!.spinOff.indices) {
                                    listSpinOff.add(related!!.spinOff[aux].name)
                                }
                                mangaSpinOff.text = listSpinOff.toString()
                                listSpinOff.clear()
                            }
                            shimmerLayout.stopShimmer()
                            shimmerLayout.visibility = View.GONE
                            overviewManga.visibility = View.VISIBLE
                        }
                    }
                }
                is ScreenStateHelper.Error -> {

                }
                else -> {

                }
            }
        })
    }
}