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
import com.victor.myan.enums.TypesRequest
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
                        "Anime" -> {
                            progressBar.visibility = View.VISIBLE
                            val animeSearch = arrayListOf<Anime>()
                            animeAdapter = AnimeAdapter(animeSearch)
                            recyclerViewSearch.adapter = animeAdapter
                            recyclerViewSearch.layoutManager = GridLayoutManager(context, 2)

                            api.search(
                                TypesRequest.Anime.type,
                                search.query.toString(),
                                ConstsEnum.LimitSearch.valor
                            ).enqueue(object :
                                Callback<JsonObject> {
                                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                                    messageSearch.text =
                                        auxServicesHelper.capitalize("not found this anime, try another please")
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
                                                animeResponse.getAsJsonArray(TypesRequest.Results.type)
                                            if (results != null) {
                                                for (result in 0 until results.size()) {
                                                    val animeFound: JsonObject? =
                                                        results.get(result) as JsonObject?
                                                    if (animeFound != null) {
                                                        val anime = Anime()

                                                        anime.title = animeFound.get("title").asString
                                                        anime.mal_id =
                                                            animeFound.get("mal_id").asInt.toString()
                                                        anime.episodes = animeFound.get("episodes").asInt
                                                        anime.image_url =
                                                            animeFound.get("image_url").asString
                                                        anime.airing_start = animeFound.get("start_date").asString
                                                        anime.score = animeFound.get("score").asDouble
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
                        "Manga" -> {
                            progressBar.visibility = View.VISIBLE
                            val mangaSearch = arrayListOf<Manga>()
                            mangaAdapter = MangaAdapter(mangaSearch)
                            recyclerViewSearch.adapter = mangaAdapter
                            recyclerViewSearch.layoutManager = GridLayoutManager(context, 2)

                            api.search(
                                TypesRequest.Manga.type,
                                search.query.toString(),
                                ConstsEnum.LimitSearch.valor
                            ).enqueue(object :
                                Callback<JsonObject> {
                                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                                    messageSearch.text =
                                        auxServicesHelper.capitalize("not found this manga, try another please")
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
                                                mangaResponse.getAsJsonArray(TypesRequest.Results.type)
                                            if (results != null) {
                                                for (result in 0 until results.size()) {
                                                    val mangaFound : JsonObject? =
                                                        results.get(result) as JsonObject?
                                                    if (mangaFound != null) {
                                                        val manga = Manga()

                                                        manga.title = mangaFound.get("title").asString
                                                        manga.mal_id =
                                                            mangaFound.get("mal_id").asInt.toString()
                                                        manga.image_url =
                                                            mangaFound.get("image_url").asString
                                                        manga.start_date = mangaFound.get("start_date").asString
                                                        manga.volumes = mangaFound.get("volumes").asInt
                                                        manga.score = mangaFound.get("score").asDouble
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