package com.victor.myan.fragments.tablayouts.characterDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.adapter.AnimeAdapter
import com.victor.myan.databinding.FragmentCharacterAnimeBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Anime
import com.victor.myan.viewmodel.CharacterViewModel

class CharacterAnimeFragment : Fragment() {

    private lateinit var binding : FragmentCharacterAnimeBinding
    private lateinit var animeAdapter: AnimeAdapter
    private val characterViewModel by lazy {
        ViewModelProvider(this).get(CharacterViewModel::class.java)
    }

    companion object {
        fun newInstance(mal_id : Int): CharacterAnimeFragment {
            val characterAnimeFragment = CharacterAnimeFragment()
            val args = Bundle()
            args.putInt("mal_id", mal_id)
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
        val malID = arguments?.getInt("mal_id")!!

        characterViewModel.getCharacterAnimeApi(malID)
        characterViewModel.characterAnimeList.observe(viewLifecycleOwner, { state ->
            processCharacterAnimeResponse(state)
        })
    }

    private fun processCharacterAnimeResponse(state: ScreenStateHelper<List<Anime>?>) {
        val malID = arguments?.getInt("mal_id")!!
        val emptyText = binding.emptyListTextView
        val characterAnimeRecyclerView = binding.recyclerView.recyclerViewVertical

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
                    characterAnimeRecyclerView.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                    characterAnimeRecyclerView.adapter = animeAdapter
                    characterAnimeRecyclerView.visibility = View.VISIBLE
                }
            }
            is ScreenStateHelper.Empty -> {
                emptyText.text = state.message
                emptyText.visibility = View.VISIBLE
                characterAnimeRecyclerView.visibility = View.GONE
            }
            is ScreenStateHelper.Error -> {
                characterViewModel.getCharacterAnimeApi(malID)
            }
        }
    }
}