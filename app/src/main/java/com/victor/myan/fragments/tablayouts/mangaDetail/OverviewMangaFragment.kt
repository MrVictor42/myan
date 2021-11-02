package com.victor.myan.fragments.tablayouts.mangaDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.myan.R
import com.victor.myan.databinding.FragmentOverviewMangaBinding
import com.victor.myan.fragments.tablayouts.animeDetail.OverviewAnimeFragment

class OverviewMangaFragment : Fragment() {

    private lateinit var binding : FragmentOverviewMangaBinding

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
}