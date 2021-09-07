package com.victor.myan.fragments.tablayouts.characterDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.victor.myan.adapter.AnimeAdapter
import com.victor.myan.databinding.FragmentCharacterAnimeBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Anime
import com.victor.myan.viewmodel.CharacterAnimeViewModel

class CharacterAnimeFragment : Fragment() {

    private lateinit var binding : FragmentCharacterAnimeBinding
    private lateinit var animeAdapter: AnimeAdapter

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
        val viewModel : CharacterAnimeViewModel by viewModels { CharacterAnimeViewModel.CharacterAnimeFactory(malID) }

        viewModel.characterAnimeLiveData.observe(this, { state ->
            processCharacterAnimeResponse(state)
        })
    }

    private fun processCharacterAnimeResponse(state: ScreenStateHelper<List<Anime>>?) {

        val characterAnimeRecyclerView = binding.recyclerViewCharacterAnime
        val emptyTextView = binding.emptyListTextView

        when(state) {
            is ScreenStateHelper.Loading -> {

            }
            is ScreenStateHelper.Success -> {
                if(state.data != null) {
                    val characterAnime = state.data
                    characterAnimeRecyclerView.setHasFixedSize(true)
                    characterAnimeRecyclerView.setItemViewCacheSize(10)
                    animeAdapter = AnimeAdapter()
                    animeAdapter.submitList(characterAnime)
                    characterAnimeRecyclerView.layoutManager = GridLayoutManager(context, 2 , GridLayout.VERTICAL, false)
                    characterAnimeRecyclerView.adapter = animeAdapter
                    characterAnimeRecyclerView.visibility = View.VISIBLE
                }
            }
            is ScreenStateHelper.Empty -> {
                emptyTextView.text = state.message
                emptyTextView.visibility = View.VISIBLE
            }
            is ScreenStateHelper.Error -> {


            }
        }
    }
}