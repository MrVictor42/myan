package com.victor.myan.fragments.tablayouts.listsDetail.personalList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.myan.databinding.FragmentPersonalListMangaBinding

class PersonalListManga : Fragment() {

    private lateinit var binding : FragmentPersonalListMangaBinding

    companion object {
        fun newInstance(id : String): PersonalListManga {
            val personalListManga = PersonalListManga()
            val args = Bundle()
            args.putString("ID", id)
            personalListManga.arguments = args
            return personalListManga
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalListMangaBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}