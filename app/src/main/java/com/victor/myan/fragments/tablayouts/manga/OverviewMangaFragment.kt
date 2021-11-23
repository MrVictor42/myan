package com.victor.myan.fragments.tablayouts.manga

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.victor.myan.R
import com.victor.myan.adapter.AdaptationItemAdapter
import com.victor.myan.adapter.GenreItemAdapter
import com.victor.myan.databinding.FragmentOverviewMangaBinding
import com.victor.myan.enums.MangaStatusEnum
import com.victor.myan.enums.StatusEnum
import com.victor.myan.fragments.tablayouts.lists.personalList.ListDialogFragment
import com.victor.myan.helper.AuxFunctionsHelper
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Adaptation
import com.victor.myan.model.Genre
import com.victor.myan.model.Manga
import com.victor.myan.viewmodel.MangaViewModel

class OverviewMangaFragment : Fragment() {

    private lateinit var binding : FragmentOverviewMangaBinding
    private lateinit var genreItemAdapter: GenreItemAdapter
    private lateinit var adaptationItemAdapter: AdaptationItemAdapter
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
        val btnAddList = binding.btnAddList
        val mangaScore = binding.mangaScore
        val mangaAuthors = binding.mangaAuthor
        val mangaTitle = binding.mangaTitle
        val mangaTitleSynonyms = binding.mangaTitleSynonyms
        val mangaStatus = binding.mangaStatus
        val volumesChapter = binding.volumesChapters
        val adaptationsText = binding.mangaAdaptationText
        val mangaAdaptations = binding.recyclerViewAdaptations
        val typeYear = binding.typeYear
        val mangaGenres = binding.recyclerViewGenres
        val expandableTextViewSynopsis = binding.expandableTextViewSynopsis.expandableTextView
        val listAuthors: MutableList<String> = mutableListOf()
        val listAdaptations: MutableList<Adaptation> = mutableListOf()
        val listGenres : MutableList<Genre> = arrayListOf()
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
                                mangaGenres.layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
                                mangaGenres.adapter = genreItemAdapter
                            }

                            expandableTextViewSynopsis.text = synopsis

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
                                    mangaAdaptations.layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
                                    mangaAdaptations.adapter = adaptationItemAdapter
                                }
                            }

                            btnAddList.setOnClickListener {
                                val manga = Manga()

                                manga.malID = malID
                                manga.imageURL = imageURL
                                manga.title = title
                                manga.status = status

                                ListDialogFragment(null, manga).show(childFragmentManager, "OverViewMangaFragment")
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