package com.victor.myan.screens.animeDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.adapter.CharactersAdapter
import com.victor.myan.databinding.FragmentCharacterBinding
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val characterRecyclerView = binding.animeCharacter
        characterAdapter = CharactersAdapter()

        val malID = arguments?.getString("mal_id").toString()
        val viewModel : CharacterViewModel by viewModels { CharacterViewModel.CharacterFactory(malID)}

        viewModel.response.observe(viewLifecycleOwner, { character ->
            val characterList = character.characters
            characterRecyclerView.setHasFixedSize(true)
            characterRecyclerView.setItemViewCacheSize(10)
            characterAdapter.submitList(characterList)
            characterRecyclerView.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            characterRecyclerView.adapter = characterAdapter
        })

        viewModel.loading.observe(viewLifecycleOwner, { loading ->
            binding.progressBar.isVisible = loading
        })

        viewModel.failed.observe(viewLifecycleOwner, {
            Toast.makeText(context, "Falhou :/", Toast.LENGTH_SHORT).show()
        })
    }
}