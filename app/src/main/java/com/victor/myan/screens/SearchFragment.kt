package com.victor.myan.screens

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.adapter.AnimeAdapter
import com.victor.myan.adapter.MangaAdapter
import com.victor.myan.databinding.FragmentSearchBinding
import com.victor.myan.model.Anime
import com.victor.myan.helper.AuxFunctionsHelper
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Manga
import com.victor.myan.viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    private lateinit var binding : FragmentSearchBinding
    private lateinit var animeAdapter: AnimeAdapter
    private lateinit var mangaAdapter: MangaAdapter
    private val auxServicesHelper = AuxFunctionsHelper()
    private val limit = 16

    private val searchViewModel by lazy {
        ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    companion object {
        fun newInstance(): SearchFragment {
            val searchFragment = SearchFragment()
            val args = Bundle()
            searchFragment.arguments = args
            return searchFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val searchButton = binding.searchButton
        val radioGroup = binding.selectionType
        val search = binding.searchView
        val messageSearch = binding.messageSearch
        val recyclerViewSearch = binding.recyclerViewSearch

        searchButton.setOnClickListener {
            val selectionType = radioGroup.checkedRadioButtonId
            val choice: RadioButton = view.findViewById(selectionType)
            hideKeyboard()

            when {
                search.query.isEmpty() -> {
                    messageSearch.text =
                        auxServicesHelper.capitalize("please, insert a name to anime/manga or movie")
                    messageSearch.setTextColor(Color.RED)
                }
                search.query.length <= 2 -> {
                    messageSearch.text =
                        auxServicesHelper.capitalize(
                            "please, insert a name to anime/manga or movie with more 2 characters"
                        )
                    messageSearch.setTextColor(Color.RED)
                }
                else -> {
                    when(choice.text) {
                        "Manga" -> {
                            recyclerViewSearch.visibility = View.GONE
                            searchViewModel.getResultsListApi(
                                choice.text.toString(), search.query.toString(), limit
                            )
                            searchViewModel.resultsManga.observe(viewLifecycleOwner, { state ->
                                processMangaListSearchResponse(state)
                            })
                        } else -> {
                            recyclerViewSearch.visibility = View.GONE
                            searchViewModel.getResultsListApi(
                                choice.text.toString(), search.query.toString(), limit
                            )
                            searchViewModel.resultsAnimeMovie.observe(viewLifecycleOwner, { state ->
                                processAnimeMovieListSearchResponse(state)
                            })
                        }
                    }
                }
            }
        }
    }

    private fun processAnimeMovieListSearchResponse(state: ScreenStateHelper<List<Anime>?>?) {
        val progressBar = binding.progressBarSearch
        val recyclerViewSearch = binding.recyclerViewSearch
        val messageSearch = binding.messageSearch

        when(state) {
            is ScreenStateHelper.Loading -> {
                progressBar.visibility = View.VISIBLE
            }
            is ScreenStateHelper.Empty -> {
                progressBar.visibility = View.GONE
                messageSearch.text = state.message
            }
            is ScreenStateHelper.Error -> {
                Log.e("Message error", state.message.toString())
                progressBar.visibility = View.GONE
            }
            is ScreenStateHelper.Success -> {
                if(state.data != null) {
                    val animeMovieList = state.data
                    recyclerViewSearch.setHasFixedSize(true)
                    recyclerViewSearch.setItemViewCacheSize(10)
                    animeAdapter = AnimeAdapter()
                    animeAdapter.submitList(animeMovieList)
                    recyclerViewSearch.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                    recyclerViewSearch.adapter = animeAdapter
                    recyclerViewSearch.visibility = View.VISIBLE
                    messageSearch.visibility = View.GONE
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun processMangaListSearchResponse(state: ScreenStateHelper<List<Manga>?>?) {
        val progressBar = binding.progressBarSearch
        val recyclerViewSearch = binding.recyclerViewSearch
        val messageSearch = binding.messageSearch

        when(state) {
            is ScreenStateHelper.Loading -> {
                progressBar.visibility = View.VISIBLE
            }
            is ScreenStateHelper.Empty -> {
                progressBar.visibility = View.GONE
                messageSearch.text = state.message
            }
            is ScreenStateHelper.Error -> {
                Log.e("Message error", state.message.toString())
                progressBar.visibility = View.GONE
            }
            is ScreenStateHelper.Success -> {
                if(state.data != null) {
                    val mangaList = state.data
                    recyclerViewSearch.setHasFixedSize(true)
                    recyclerViewSearch.setItemViewCacheSize(10)
                    mangaAdapter = MangaAdapter()
                    mangaAdapter.submitList(mangaList)
                    recyclerViewSearch.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                    recyclerViewSearch.adapter = mangaAdapter
                    recyclerViewSearch.visibility = View.VISIBLE
                    messageSearch.visibility = View.GONE
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}