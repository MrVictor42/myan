package com.victor.myan.fragments

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
import com.victor.myan.viewmodel.ActorViewModel
import com.victor.myan.viewmodel.CharacterViewModel

class CharacterFragment(
    private val malID : Int, private val type : String
) : Fragment() {

    private lateinit var binding: FragmentCharacterBinding
    private lateinit var characterAdapter: CharactersAdapter
    private val actorViewModel by lazy {
        ViewModelProvider(this)[ActorViewModel::class.java]
    }
    private val characterViewModel by lazy {
        ViewModelProvider(this)[CharacterViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val characterRecyclerView = binding.recyclerView
        val shimmerLayout = binding.shimmerLayout
        val emptyText = binding.emptyListText
        val errorOptions = binding.errorOptions.errorOptions
        val btnRefresh = binding.errorOptions.btnRefresh
        
        when(type) {
            "anime" -> {
                characterViewModel.getCharacterListApi(malID)
                characterViewModel.characterList.observe(viewLifecycleOwner, { characters ->
                    when (characters) {
                        is ScreenStateHelper.Loading -> {

                        }
                        is ScreenStateHelper.Success -> {
                            if (characters.data != null) {
                                val characterList = characters.data
                                characterRecyclerView.layoutManager =
                                    GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                                characterAdapter = CharactersAdapter()
                                characterAdapter.setData(characterList)
                                characterRecyclerView.adapter = characterAdapter
                                shimmerLayout.visibility = View.GONE
                                characterRecyclerView.visibility = View.VISIBLE
                            }
                        }
                        is ScreenStateHelper.Empty -> {
                            emptyText.text = characters.message
                            emptyText.visibility = View.VISIBLE
                            characterRecyclerView.visibility = View.GONE
                            shimmerLayout.visibility = View.GONE
                        }
                        is ScreenStateHelper.Error -> {
                            errorOptions.visibility = View.VISIBLE
                            shimmerLayout.visibility = View.GONE

                            btnRefresh.setOnClickListener {
                                onViewCreated(view, savedInstanceState)

                                errorOptions.visibility = View.GONE
                            }
                        }
                        else -> {

                        }
                    }
                })
            }
            "actor" -> {
                actorViewModel.getActorCharacterApi(malID)
                actorViewModel.actorCharacterList.observe(viewLifecycleOwner, { actorCharacter ->
                    when(actorCharacter) {
                        is ScreenStateHelper.Loading -> {

                        }
                        is ScreenStateHelper.Success -> {
                            if (actorCharacter.data != null) {
                                val characterList = actorCharacter.data
                                characterRecyclerView.layoutManager =
                                    GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                                characterAdapter = CharactersAdapter()
                                characterAdapter.setData(characterList)
                                characterRecyclerView.adapter = characterAdapter
                                shimmerLayout.visibility = View.GONE
                                characterRecyclerView.visibility = View.VISIBLE
                            }
                        }
                        is ScreenStateHelper.Error -> {
                            emptyText.text = actorCharacter.message
                            emptyText.visibility = View.VISIBLE
                            characterRecyclerView.visibility = View.GONE
                            shimmerLayout.visibility = View.GONE
                        }
                        else -> {
                            errorOptions.visibility = View.VISIBLE
                            shimmerLayout.visibility = View.GONE

                            btnRefresh.setOnClickListener {
                                onViewCreated(view, savedInstanceState)

                                errorOptions.visibility = View.GONE
                            }
                        }
                    }
                })
            }
        }
    }
}