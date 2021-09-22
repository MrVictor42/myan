package com.victor.myan.fragments.tablayouts.listsDetail.personalList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.myan.databinding.FragmentPersonalListAnimeBinding

class PersonalListAnime : Fragment() {

    private lateinit var binding : FragmentPersonalListAnimeBinding

    companion object {
        fun newInstance(id : String): PersonalListAnime {
            val personalListAnime = PersonalListAnime()
            val args = Bundle()
            args.putString("ID", id)
            personalListAnime.arguments = args
            return personalListAnime
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalListAnimeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}