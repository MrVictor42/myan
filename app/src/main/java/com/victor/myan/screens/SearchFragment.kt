package com.victor.myan.screens

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
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.victor.myan.adapter.AnimeAdapter
import com.victor.myan.adapter.MangaAdapter
import com.victor.myan.api.AnimeApi
import com.victor.myan.enums.MessagesEnum
import com.victor.myan.enums.ConstsEnum
import com.victor.myan.helper.JikanApiInstanceHelper
import com.victor.myan.databinding.FragmentSearchBinding
import com.victor.myan.enums.TypesEnum
import com.victor.myan.enums.VariablesEnum
import com.victor.myan.model.Anime
import com.victor.myan.helper.AuxFunctionsHelper
import com.victor.myan.model.Manga
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    private lateinit var binding : FragmentSearchBinding
    private lateinit var animeAdapter: AnimeAdapter
    private lateinit var mangaAdapter: MangaAdapter
    private val auxServicesHelper = AuxFunctionsHelper()

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
        val progressBar = binding.progressBarSearch
        val recyclerViewSearch = binding.recyclerViewSearch
        val api = JikanApiInstanceHelper.getJikanApiInstance().create(AnimeApi::class.java)

        searchButton.setOnClickListener {
            val selectionType = radioGroup.checkedRadioButtonId
            val choice: RadioButton = view.findViewById(selectionType)
            hideKeyboard()

            when {
                search.query.isEmpty() -> {
                    messageSearch.text =
                        auxServicesHelper.capitalize(MessagesEnum.EmptyQuery.message)
                    messageSearch.setTextColor(Color.RED)
                }
                search.query.length <= ConstsEnum.MinLengthSearch.valor -> {
                    messageSearch.text =
                        auxServicesHelper.capitalize(MessagesEnum.MinLengthQuery.message)
                    messageSearch.setTextColor(Color.RED)
                }
                else -> {
                    when (choice.text) {
                        TypesEnum.Anime.name -> {
                            progressBar.visibility = View.VISIBLE
                            val animeSearch = arrayListOf<Anime>()
                            animeAdapter = AnimeAdapter(animeSearch)
                            recyclerViewSearch.adapter = animeAdapter
                            recyclerViewSearch.layoutManager = GridLayoutManager(context, 2)

                            api.search(
                                TypesEnum.Anime.type,
                                search.query.toString(),
                                ConstsEnum.LimitSearch.valor
                            ).enqueue(object :
                                Callback<JsonObject> {
                                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                                    messageSearch.text =
                                        auxServicesHelper.capitalize(MessagesEnum.NotFoundQuery.message)
                                    messageSearch.setTextColor(Color.RED)
                                }

                                override fun onResponse(
                                    call: Call<JsonObject>,
                                    response: Response<JsonObject>
                                ) {
                                    if (response.isSuccessful) {
                                        val animeResponse = response.body()
                                        animeAdapter.anime.clear()
                                        if (animeResponse != null) {
                                            val results: JsonArray? =
                                                animeResponse.getAsJsonArray(TypesEnum.Results.type)
                                            if (results != null) {
                                                for (result in 0 until results.size()) {
                                                    val animeFound: JsonObject? =
                                                        results.get(result) as JsonObject?
                                                    if (animeFound != null) {
                                                        val anime = Anime()

                                                        anime.title = animeFound.get(VariablesEnum.Title.variable).asString
                                                        anime.mal_id =
                                                            animeFound.get(VariablesEnum.MalID.variable).asInt.toString()
                                                        anime.episodes = animeFound.get(VariablesEnum.Episodes.variable).asInt
                                                        anime.image_url =
                                                            animeFound.get(VariablesEnum.Image.variable).asString
                                                        anime.score = animeFound.get(VariablesEnum.Score.variable).asDouble

                                                        if(animeFound.get(VariablesEnum.StartDate.variable).toString() == "null") {
                                                            anime.airing_start = "null"
                                                        } else {
                                                            anime.airing_start = animeFound.get(VariablesEnum.StartDate.variable).asString
                                                        }


                                                        animeAdapter.anime.add(anime)
                                                    }
                                                }
                                                animeAdapter.notifyDataSetChanged()
                                            }
                                        }
                                    }
                                    progressBar.visibility = View.INVISIBLE
                                }
                            })
                        }
                        TypesEnum.Manga.name -> {
                            progressBar.visibility = View.VISIBLE
                            val mangaSearch = arrayListOf<Manga>()
                            mangaAdapter = MangaAdapter(mangaSearch)
                            recyclerViewSearch.adapter = mangaAdapter
                            recyclerViewSearch.layoutManager = GridLayoutManager(context, 2)

                            api.search(
                                TypesEnum.Manga.type,
                                search.query.toString(),
                                ConstsEnum.LimitSearch.valor
                            ).enqueue(object :
                                Callback<JsonObject> {
                                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                                    messageSearch.text =
                                        auxServicesHelper.capitalize(MessagesEnum.NotFoundQuery.message)
                                    messageSearch.setTextColor(Color.RED)
                                }

                                override fun onResponse(
                                    call: Call<JsonObject>,
                                    response: Response<JsonObject>
                                ) {
                                    if (response.isSuccessful) {
                                        val mangaResponse = response.body()
                                        mangaAdapter.manga.clear()
                                        if (mangaResponse != null) {
                                            val results: JsonArray? =
                                                mangaResponse.getAsJsonArray(TypesEnum.Results.type)
                                            if (results != null) {
                                                for (result in 0 until results.size()) {
                                                    val mangaFound : JsonObject? =
                                                        results.get(result) as JsonObject?
                                                    if (mangaFound != null) {
                                                        val manga = Manga()

                                                        manga.title = mangaFound.get(VariablesEnum.Title.variable).asString
                                                        manga.mal_id =
                                                            mangaFound.get(VariablesEnum.MalID.variable).asInt.toString()
                                                        manga.image_url =
                                                            mangaFound.get(VariablesEnum.Image.variable).asString
                                                        manga.volumes = mangaFound.get(VariablesEnum.Volumes.variable).asInt
                                                        manga.score = mangaFound.get(VariablesEnum.Score.variable).asDouble

                                                        if(mangaFound.get(VariablesEnum.StartDate.variable).toString() == "null") {
                                                            manga.start_date = "null"
                                                        } else {
                                                            manga.start_date = mangaFound.get(VariablesEnum.StartDate.variable).asString
                                                        }

                                                        mangaAdapter.manga.add(manga)
                                                    }
                                                }
                                                mangaAdapter.notifyDataSetChanged()
                                            }
                                        }
                                    }
                                    progressBar.visibility = View.INVISIBLE
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