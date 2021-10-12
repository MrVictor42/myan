package com.victor.myan.fragments.tablayouts.characterDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.adapter.MangaAdapter
import com.victor.myan.databinding.FragmentCharacterMangaBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Manga
import com.victor.myan.viewmodel.CharacterViewModel

class CharacterMangaFragment : Fragment() {

    private lateinit var binding : FragmentCharacterMangaBinding
    private lateinit var mangaAdapter: MangaAdapter
    private val characterViewModel by lazy {
        ViewModelProvider(this).get(CharacterViewModel::class.java)
    }
    private val TAG = CharacterMangaFragment::class.java.simpleName

    companion object {
        fun newInstance(mal_id : Int): CharacterMangaFragment {
            val mangaCharacterFragment = CharacterMangaFragment()
            val args = Bundle()
            args.putInt("mal_id", mal_id)
            mangaCharacterFragment.arguments = args
            return mangaCharacterFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterMangaBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getInt("mal_id")!!

        characterViewModel.getCharacterMangaApi(malID)
        characterViewModel.characterMangaList.observe(viewLifecycleOwner, { state ->
            processCharacterMangaResponse(state)
        })
    }

    private fun processCharacterMangaResponse(state: ScreenStateHelper<List<Manga>?>) {
        val characterMangaRecyclerView = binding.recyclerView.recyclerViewVertical
        val emptyText = binding.emptyListTextView
        val shimmerLayout = binding.shimmerLayout

        when(state) {
            is ScreenStateHelper.Loading -> {
                shimmerLayout.startShimmer()
                Log.i(TAG, "CharacterMangaFragment Loading...")
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

                    shimmerLayout.stopShimmer()
                    shimmerLayout.visibility = View.GONE
                    characterMangaRecyclerView.visibility = View.VISIBLE

                    Log.i(TAG, "Success in loading character manga")
                }
            }
            is ScreenStateHelper.Empty -> {
                emptyText.text = state.message
                emptyText.visibility = View.VISIBLE
                characterMangaRecyclerView.visibility = View.GONE
            }
            is ScreenStateHelper.Error -> {
                Log.e(TAG, "Error CharacterMangaFragment in CharacterVoiceFragment with code ${state.message}")
            }
        }
    }
}