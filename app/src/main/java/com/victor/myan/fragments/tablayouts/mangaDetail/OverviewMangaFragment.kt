package com.victor.myan.fragments.tablayouts.mangaDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.victor.myan.R
import com.victor.myan.databinding.FragmentOverviewMangaBinding
import com.victor.myan.fragments.tablayouts.animeDetail.OverviewAnimeFragment
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.viewmodel.AnimeViewModel
import com.victor.myan.viewmodel.MangaViewModel

class OverviewMangaFragment : Fragment() {

    private lateinit var binding : FragmentOverviewMangaBinding
    private val TAG = OverviewMangaFragment::class.java.simpleName
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getInt("mal_id")!!
        val mangaImage = binding.image
        val mangaRank = binding.mangaRank
        val mangaScore = binding.mangaScore
        val mangaAuthor = binding.mangaAuthor
        val mangaTitle = binding.mangaTitle
        val mangaTitleSynonyms = binding.mangaTitleSynonyms
        val mangaStatus = binding.mangaStatus
        val typeYear = binding.typeYear
        var listGenres = ""
        val expandableTextViewSynopsis = binding.expandableTextViewSynopsis.expandableTextView
        val expandableTextViewBackground = binding.expandableTextViewBackground.expandableTextView

        mangaViewModel.getManga(malID)
        mangaViewModel.manga.observe(viewLifecycleOwner, { manga ->
            when(manga) {
                is ScreenStateHelper.Loading -> {
                    Log.i(TAG, "OverviewMangaFragment Loading...")
                }
                is ScreenStateHelper.Success -> {
                    if(manga.data != null) {
                        with(manga.data) {
                            mangaTitle.text = title
                            Glide.with(view.context!!).load(imageUrl).into(mangaImage)

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
                        }
                    }
                }
                else -> {

                }
            }
        })
    }
}