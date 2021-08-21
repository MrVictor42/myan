package com.victor.myan.screens.animeDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.victor.myan.adapter.CharactersAdapter
import com.victor.myan.databinding.FragmentCharacterBinding
import com.victor.myan.helper.ScreenState
import com.victor.myan.model.Character
import com.victor.myan.viewmodel.CharacterViewModel

class CharacterFragment : Fragment() {

    companion object {
        fun newInstance(mal_id : String): CharacterFragment {
            val characterFragment = CharacterFragment()
            val args = Bundle()
            args.putString("mal_id", mal_id)
            characterFragment.arguments = args
            return characterFragment
        }
    }

    private lateinit var binding : FragmentCharacterBinding
    private lateinit var characterAdapter : CharactersAdapter
    private lateinit var characterRecyclerView : RecyclerView
    private lateinit var progressBar : ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getString("mal_id").toString()
        val viewModel : CharacterViewModel by viewModels { CharacterViewModel.CharacterFactory(malID)}

        viewModel.characterLiveData.observe(this, { state ->
            processCharacterResponse(state)
        })
    }

    private fun processCharacterResponse(state : ScreenState<List<Character>?>) {

        characterRecyclerView = binding.animeCharacter
        progressBar = binding.progressBar

        when(state) {
            is ScreenState.Loading -> {
                progressBar.visibility = View.VISIBLE
            }
            is ScreenState.Success -> {
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
            is ScreenState.Error -> {
                progressBar.visibility = View.VISIBLE
                val view = progressBar.rootView
                Snackbar.make(view, "Connection with internet not found...", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}