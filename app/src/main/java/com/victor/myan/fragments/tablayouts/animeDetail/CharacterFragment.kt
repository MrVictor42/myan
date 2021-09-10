package com.victor.myan.fragments.tablayouts.animeDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.adapter.CharactersAdapter
import com.victor.myan.databinding.FragmentCharacterBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Character
import com.victor.myan.viewmodel.CharacterViewModel

class CharacterFragment : Fragment() {

    private lateinit var binding : FragmentCharacterBinding
    private lateinit var characterAdapter : CharactersAdapter
    private val characterViewModel by lazy {
        ViewModelProvider(this).get(CharacterViewModel::class.java)
    }

    companion object {
        fun newInstance(mal_id : Int): CharacterFragment {
            val characterFragment = CharacterFragment()
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
        binding = FragmentCharacterBinding.inflate(layoutInflater, container, false)
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
        val malID = arguments?.getInt("mal_id")!!
        val characterRecyclerView = binding.recyclerView.recyclerViewVertical

        when(state) {
            is ScreenStateHelper.Loading -> {

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
                }
            }
            is ScreenStateHelper.Error -> {
                characterViewModel.getCharacterListApi(malID)
            }
            else -> {
                // Nothing to do
            }
        }
    }
}