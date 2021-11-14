package com.victor.myan.fragments.tablayouts.anime

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
import com.victor.myan.viewmodel.CharacterViewModel

class CharacterFragment : Fragment() {

    private lateinit var binding: FragmentCharacterBinding
    private lateinit var characterAdapter: CharactersAdapter
    private val characterViewModel by lazy {
        ViewModelProvider(this)[CharacterViewModel::class.java]
    }

    companion object {
        fun newInstance(mal_id: Int): CharacterFragment {
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
        val characterRecyclerView = binding.recyclerView
        val shimmerLayout = binding.shimmerLayout

        characterViewModel.getCharacterListApi(malID)
        characterViewModel.characterList.observe(viewLifecycleOwner, { characters ->
            when (characters) {
                is ScreenStateHelper.Loading -> {
                    shimmerLayout.startShimmer()
                }
                is ScreenStateHelper.Success -> {
                    if (characters.data != null) {
                        val characterList = characters.data
                        characterRecyclerView.layoutManager =
                            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                        characterAdapter = CharactersAdapter()
                        characterAdapter.setData(characterList)
                        characterRecyclerView.adapter = characterAdapter
                        shimmerLayout.stopShimmer()
                        shimmerLayout.visibility = View.GONE
                        characterRecyclerView.visibility = View.VISIBLE
                    }
                }
                is ScreenStateHelper.Error -> {

                }
                else -> {

                }
            }
        })
    }
}