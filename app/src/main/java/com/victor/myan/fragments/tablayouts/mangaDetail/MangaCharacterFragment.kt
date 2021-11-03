package com.victor.myan.fragments.tablayouts.mangaDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.R
import com.victor.myan.adapter.CharactersAdapter
import com.victor.myan.databinding.FragmentMangaCharacterBinding
import com.victor.myan.fragments.tablayouts.animeDetail.CharacterFragment
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Character
import com.victor.myan.viewmodel.CharacterViewModel

class MangaCharacterFragment : Fragment() {

    private lateinit var binding : FragmentMangaCharacterBinding
    private lateinit var characterAdapter : CharactersAdapter
    private val characterViewModel by lazy {
        ViewModelProvider(this)[CharacterViewModel::class.java]
    }
    private val TAG = MangaCharacterFragment::class.java.simpleName

    companion object {
        fun newInstance(mal_id : Int): MangaCharacterFragment {
            val characterFragment = MangaCharacterFragment()
            val args = Bundle()
            args.putInt("mal_id", mal_id)
            characterFragment.arguments = args
            return characterFragment
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
        val malID = arguments?.getInt("mal_id")!!

        characterViewModel.getCharacterListApi(malID)
        characterViewModel.characterList.observe(viewLifecycleOwner, { state ->
            processCharacterListResponse(state)
        })
    }

    private fun processCharacterListResponse(state : ScreenStateHelper<List<Character>?>) {
        val characterRecyclerView = binding.recyclerView.recyclerViewVertical
        val shimmerLayoutCharacter = binding.shimmerLayoutCharacter

        when(state) {
            is ScreenStateHelper.Loading -> {
                shimmerLayoutCharacter.startShimmer()
                Log.i(TAG, "Loading Character List Airing")
            }
            is ScreenStateHelper.Success -> {
                if(state.data != null) {
                    val characterList = state.data
                    characterRecyclerView.setHasFixedSize(true)
                    characterRecyclerView.setItemViewCacheSize(10)
                    characterAdapter = CharactersAdapter()
                    characterAdapter.submitList(characterList)
                    characterRecyclerView.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                    characterRecyclerView.adapter = characterAdapter
                    shimmerLayoutCharacter.stopShimmer()
                    shimmerLayoutCharacter.visibility = View.GONE
                    characterRecyclerView.visibility = View.VISIBLE
                    Log.i(TAG, "Success Character List")
                }
            }
            is ScreenStateHelper.Error -> {
                Log.e(TAG, "Error Character List in Character Fragment With Code: ${state.message}")
            }
            else -> {
                // Nothing to do
            }
        }
    }
}