package com.victor.myan.screens.characterDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.myan.databinding.FragmentCharacterAnimeBinding

class CharacterAnimeFragment : Fragment() {

    private lateinit var binding : FragmentCharacterAnimeBinding

    companion object {
        fun newInstance(mal_id : String): CharacterAnimeFragment {
            val characterAnimeFragment = CharacterAnimeFragment()
            val args = Bundle()
            args.putString("mal_id", mal_id)
            characterAnimeFragment.arguments = args
            return characterAnimeFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterAnimeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getString("mal_id").toString()
    }
}