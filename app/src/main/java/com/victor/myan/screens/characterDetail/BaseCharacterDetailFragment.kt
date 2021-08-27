package com.victor.myan.screens.characterDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.victor.myan.adapter.CharactersAdapter
import com.victor.myan.databinding.FragmentBaseCharacterDetailBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Anime
import com.victor.myan.model.Character
import com.victor.myan.screens.animeDetail.OverviewFragment
import com.victor.myan.viewmodel.AnimeCharacterViewModel
import com.victor.myan.viewmodel.CharacterViewModel

class BaseCharacterDetailFragment : Fragment() {

    private lateinit var binding : FragmentBaseCharacterDetailBinding

    companion object {
        fun newInstance(mal_id : String): BaseCharacterDetailFragment {
            val baseFragment = BaseCharacterDetailFragment()
            val args = Bundle()
            args.putString("mal_id", mal_id)
            baseFragment.arguments = args
            return baseFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseCharacterDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getString("mal_id").toString()
        val viewModel : CharacterViewModel by viewModels { CharacterViewModel.CharacterFactory(malID) }

        viewModel.characterLiveData.observe(this, { state ->
            processCharacterResponse(state)
        })
    }

    private fun processCharacterResponse(state: ScreenStateHelper<Character>?) {

        val about = binding.about

        when(state) {
            is ScreenStateHelper.Loading -> {

            }
            is ScreenStateHelper.Success -> {
                if(state.data != null) {
                    with(state.data) {
                        about.text = about.toString()
                    }
                }
            }
            is ScreenStateHelper.Error -> {
//                Snackbar.make(view, "Not found characters ...", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}