package com.victor.myan.screens.personDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.victor.myan.R
import com.victor.myan.databinding.FragmentAnimePersonBinding
import com.victor.myan.screens.characterDetail.CharacterAnimeFragment
import com.victor.myan.viewmodel.CharacterAnimeViewModel
import com.victor.myan.viewmodel.PersonAnimeViewModel

class AnimePersonFragment : Fragment() {

    private lateinit var binding : FragmentAnimePersonBinding

    companion object {
        fun newInstance(mal_id : String): AnimePersonFragment {
            val animePersonFragment = AnimePersonFragment()
            val args = Bundle()
            args.putString("mal_id", mal_id)
            animePersonFragment.arguments = args
            return animePersonFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimePersonBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getString("mal_id").toString()
        val viewModel : PersonAnimeViewModel by viewModels { PersonAnimeViewModel.PersonAnimeFactory(malID) }
    }
}