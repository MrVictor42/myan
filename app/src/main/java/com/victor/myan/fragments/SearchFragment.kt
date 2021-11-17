package com.victor.myan.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
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
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    private lateinit var binding : FragmentSearchBinding
    private lateinit var animeAdapter: AnimeAdapter
    private lateinit var mangaAdapter: MangaAdapter
    private val limit = 16
    private val searchViewModel by lazy {
        ViewModelProvider(this)[SearchViewModel::class.java]
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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val searchButton = binding.searchButton
        val radioGroup = binding.selectionType
        val search = binding.searchView
        val messageSearch = binding.messageSearch
        val recyclerViewSearch = binding.recyclerView
        val progressBar = binding.progressBarSearch

        searchButton.setOnClickListener {
            val selectionType = radioGroup.checkedRadioButtonId
            val choice: RadioButton = view.findViewById(selectionType)
            hideKeyboard()

            when {
                search.query.isEmpty() -> {
                    messageSearch.text = "Please, insert a name to anime/manga or movie"
                    messageSearch.setTextColor(Color.RED)
                    recyclerViewSearch.visibility = View.GONE
                }
                search.query.length <= 2 -> {
                    messageSearch.text = "Please, insert a name to anime/manga or movie with more 2 characters"
                    messageSearch.setTextColor(Color.RED)
                }
                else -> {
                    when(choice.text) {
                        "Manga" -> {
                            recyclerViewSearch.visibility = View.GONE
                            searchViewModel.getResultsListApi(
                                choice.text.toString(), search.query.toString(), limit
                            )
                            searchViewModel.resultsManga.observe(viewLifecycleOwner, { mangaList ->
                                when(mangaList) {
                                    is ScreenStateHelper.Loading -> {
                                        progressBar.visibility = View.VISIBLE
                                    }
                                    is ScreenStateHelper.Success -> {
                                        if(mangaList.data != null) {
                                            val searchMangaList = mangaList.data
                                            recyclerViewSearch.layoutManager =
                                                    GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                                            mangaAdapter = MangaAdapter()
                                            mangaAdapter.setData(searchMangaList)
                                            recyclerViewSearch.adapter = mangaAdapter
                                            recyclerViewSearch.visibility = View.VISIBLE
                                            messageSearch.visibility = View.GONE
                                            progressBar.visibility = View.GONE
                                        }
                                    }
                                    is ScreenStateHelper.Empty -> {
                                        progressBar.visibility = View.GONE
                                        messageSearch.text = mangaList.message
                                    }
                                    is ScreenStateHelper.Error -> {
                                        progressBar.visibility = View.GONE
                                    }
                                    else -> {

                                    }
                                }
                            })
                        } else -> {
                            recyclerViewSearch.visibility = View.GONE
                            searchViewModel.getResultsListApi(
                                choice.text.toString(), search.query.toString(), limit
                            )
                            searchViewModel.resultsAnimeMovie.observe(viewLifecycleOwner, { animeList ->
                                when(animeList) {
                                    is ScreenStateHelper.Loading -> {
                                        progressBar.visibility = View.VISIBLE
                                    }
                                    is ScreenStateHelper.Success -> {
                                        if(animeList.data != null) {
                                            val searchAnimeList = animeList.data
                                            recyclerViewSearch.layoutManager =
                                                    GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                                            animeAdapter = AnimeAdapter()
                                            animeAdapter.setData(searchAnimeList)
                                            recyclerViewSearch.adapter = animeAdapter
                                            recyclerViewSearch.visibility = View.VISIBLE
                                            messageSearch.visibility = View.GONE
                                            progressBar.visibility = View.GONE
                                        }
                                    }
                                    is ScreenStateHelper.Empty -> {
                                        progressBar.visibility = View.GONE
                                        messageSearch.text = animeList.message
                                    }
                                    is ScreenStateHelper.Error -> {
                                        progressBar.visibility = View.GONE
                                    }
                                    else -> {

                                    }
                                }
                            })
                        }
                    }
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