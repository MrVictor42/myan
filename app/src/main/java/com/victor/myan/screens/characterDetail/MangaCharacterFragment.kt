package com.victor.myan.screens.characterDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.myan.databinding.FragmentMangaCharacterBinding

class MangaCharacterFragment : Fragment() {

    private lateinit var binding : FragmentMangaCharacterBinding

    companion object {
        fun newInstance(mal_id : String): MangaCharacterFragment {
            val mangaCharacterFragment = MangaCharacterFragment()
            val args = Bundle()
            args.putString("mal_id", mal_id)
            mangaCharacterFragment.arguments = args
            return mangaCharacterFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMangaCharacterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getString("mal_id").toString()
    }
}