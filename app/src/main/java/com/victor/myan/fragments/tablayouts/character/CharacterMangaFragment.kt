package com.victor.myan.fragments.tablayouts.character

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
import com.victor.myan.viewmodel.CharacterViewModel

class CharacterMangaFragment : Fragment() {

    private lateinit var binding : FragmentCharacterMangaBinding
    private lateinit var mangaAdapter: MangaAdapter
    private val characterViewModel by lazy {
        ViewModelProvider(this)[CharacterViewModel::class.java]
    }

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
        Log.e("character", malID.toString())
        val characterMangaRecyclerView = binding.recyclerView
        val emptyText = binding.emptyListTextView
        val shimmerLayout = binding.shimmerLayout

        characterViewModel.getCharacterMangaApi(malID)
        characterViewModel.characterMangaList.observe(viewLifecycleOwner, { manga ->
            when(manga) {
                is ScreenStateHelper.Loading -> {
                    shimmerLayout.startShimmer()
                }
                is ScreenStateHelper.Success -> {
                    if(manga.data != null) {
                        val characterMangaList = manga.data
                        characterMangaRecyclerView.layoutManager =
                            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                        mangaAdapter = MangaAdapter()
                        mangaAdapter.setData(characterMangaList)
                        characterMangaRecyclerView.adapter = mangaAdapter
                        shimmerLayout.stopShimmer()
                        shimmerLayout.visibility = View.GONE
                        characterMangaRecyclerView.visibility = View.VISIBLE
                    }
                }
                is ScreenStateHelper.Empty -> {
                    emptyText.text = manga.message
                    emptyText.visibility = View.VISIBLE
                    characterMangaRecyclerView.visibility = View.GONE
                    shimmerLayout.stopShimmer()
                    shimmerLayout.visibility = View.GONE
                }
                is ScreenStateHelper.Error -> {

                }
            }
        })
    }
}