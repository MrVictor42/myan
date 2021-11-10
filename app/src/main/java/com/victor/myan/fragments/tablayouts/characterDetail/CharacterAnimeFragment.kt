package com.victor.myan.fragments.tablayouts.characterDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.adapter.AnimeHorizontalAdapter
import com.victor.myan.databinding.FragmentCharacterAnimeBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Anime
import com.victor.myan.viewmodel.CharacterViewModel

class CharacterAnimeFragment : Fragment() {

    private lateinit var binding : FragmentCharacterAnimeBinding
    private lateinit var animeHorizontalAdapter: AnimeHorizontalAdapter
    private val characterViewModel by lazy {
        ViewModelProvider(this).get(CharacterViewModel::class.java)
    }
    private val TAG = CharacterAnimeFragment::class.java.simpleName

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
        val emptyText = binding.emptyListTextView
        val characterAnimeRecyclerView = binding.recyclerView.recyclerViewVertical
        val shimmerLayout = binding.shimmerLayout

        when(state) {
            is ScreenStateHelper.Loading -> {
                shimmerLayout.startShimmer()
                Log.i(TAG, "CharacterAnimeFragment Loading...")
            }
            is ScreenStateHelper.Success -> {
                if(state.data != null) {
                    val characterAnime = state.data
                    characterAnimeRecyclerView.setHasFixedSize(true)
                    characterAnimeRecyclerView.setItemViewCacheSize(10)
                    animeHorizontalAdapter = AnimeHorizontalAdapter()
//                    animeAdapter.submitList(characterAnime)
                    characterAnimeRecyclerView.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                    characterAnimeRecyclerView.adapter = animeHorizontalAdapter

                    shimmerLayout.stopShimmer()
                    shimmerLayout.visibility = View.GONE
                    characterAnimeRecyclerView.visibility = View.VISIBLE

                    Log.i(TAG, "Success in loading character anime")
                }
            }
            is ScreenStateHelper.Empty -> {
                emptyText.text = state.message
                emptyText.visibility = View.VISIBLE
                characterAnimeRecyclerView.visibility = View.GONE
            }
            is ScreenStateHelper.Error -> {
                Log.e(TAG, "Error CharacterAnimeFragment in CharacterAnimeFragment with code ${state.message}")
            }
        }
    }
}