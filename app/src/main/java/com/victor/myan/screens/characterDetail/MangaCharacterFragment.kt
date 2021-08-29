package com.victor.myan.screens.characterDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.victor.myan.adapter.MangaAdapter
import com.victor.myan.databinding.FragmentMangaCharacterBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Manga
import com.victor.myan.viewmodel.CharacterMangaViewModel

class MangaCharacterFragment : Fragment() {

    private lateinit var binding : FragmentMangaCharacterBinding
    private lateinit var mangaAdapter: MangaAdapter

    companion object {
        fun newInstance(mal_id : String): MangaCharacterFragment {
            val mangaCharacterFragment = MangaCharacterFragment()
            val args = Bundle()
            args.putString("mal_id", mal_id)
            mangaCharacterFragment.arguments = args
            return mangaCharacterFragment
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
        val malID = arguments?.getString("mal_id").toString()
        val viewModel : CharacterMangaViewModel by viewModels { CharacterMangaViewModel.CharacterMangaFactory(malID) }

        viewModel.characterMangaLiveData.observe(this, { state ->
            processCharacterMangaResponse(state)
        })
    }

    private fun processCharacterMangaResponse(state: ScreenStateHelper<List<Manga>>?) {

        val characterMangaRecyclerView = binding.recyclerViewCharacterManga
        val emptyTextView = binding.emptyListTextView

        when(state) {
            is ScreenStateHelper.Loading -> {

            }
            is ScreenStateHelper.Success -> {
                if(state.data != null) {
                    val characterManga = state.data
                    characterMangaRecyclerView.setHasFixedSize(true)
                    characterMangaRecyclerView.setItemViewCacheSize(10)
                    mangaAdapter = MangaAdapter()
                    mangaAdapter.submitList(characterManga)
                    characterMangaRecyclerView.layoutManager = GridLayoutManager(context, 2 , GridLayout.VERTICAL, false)
                    characterMangaRecyclerView.adapter = mangaAdapter
                    characterMangaRecyclerView.visibility = View.VISIBLE
                }
            }
            is ScreenStateHelper.Empty -> {
                emptyTextView.text = state.message
                emptyTextView.visibility = View.VISIBLE
            }
            is ScreenStateHelper.Error -> {
                val view = binding.fragmentCharacterManga
                Snackbar.make(view, state.message.toString(), Snackbar.LENGTH_LONG).show()
            }
        }
    }
}