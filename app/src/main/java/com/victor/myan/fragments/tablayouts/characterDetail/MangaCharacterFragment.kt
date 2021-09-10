package com.victor.myan.fragments.tablayouts.characterDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.adapter.MangaAdapter
import com.victor.myan.databinding.FragmentMangaCharacterBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Manga
import com.victor.myan.viewmodel.CharacterViewModel

class MangaCharacterFragment : Fragment() {

    private lateinit var binding : FragmentMangaCharacterBinding
    private lateinit var mangaAdapter: MangaAdapter
    private val characterViewModel by lazy {
        ViewModelProvider(this).get(CharacterViewModel::class.java)
    }

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

        characterViewModel.getCharacterMangaApi(malID)
        characterViewModel.characterMangaList.observe(viewLifecycleOwner, { state ->
            processCharacterMangaResponse(state)
        })
    }

    private fun processCharacterMangaResponse(state: ScreenStateHelper<List<Manga>?>) {
        val malID = arguments?.getString("mal_id").toString()
        val characterMangaRecyclerView = binding.recyclerView.recyclerViewVertical
        val emptyText = binding.emptyListTextView

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
                    characterMangaRecyclerView.layoutManager = GridLayoutManager(context, 2 , GridLayoutManager.VERTICAL, false)
                    characterMangaRecyclerView.adapter = mangaAdapter
                    characterMangaRecyclerView.visibility = View.VISIBLE
                }
            }
            is ScreenStateHelper.Empty -> {
                emptyText.text = state.message
                emptyText.visibility = View.VISIBLE
                characterMangaRecyclerView.visibility = View.GONE
            }
            is ScreenStateHelper.Error -> {
                characterViewModel.getCharacterMangaApi(malID)
            }
        }
    }
}