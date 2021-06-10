package com.victor.myan.fragments

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
import com.victor.myan.api.AnimeApi
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.databinding.FragmentSearchBinding
import com.victor.myan.enums.TypesRequest
import com.victor.myan.model.Anime
import com.victor.myan.services.impl.AuxServicesImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    private lateinit var binding : FragmentSearchBinding
    private lateinit var animeAdapter: AnimeAdapter
    private val auxServicesImpl = AuxServicesImpl()
    private val limit : Int = 16
    private val minLength : Int = 2

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
        val api = JikanApiInstance.getJikanApiInstance().create(AnimeApi::class.java)

        searchButton.setOnClickListener {
            val selectionType = radioGroup.checkedRadioButtonId
            val choice: RadioButton = view.findViewById(selectionType)
            hideKeyboard()

            when {
                search.query.isEmpty() -> {
                    messageSearch.text =
                        auxServicesImpl.capitalize("please, insert a name to anime or manga")
                    messageSearch.setTextColor(Color.RED)
                }
                search.query.length <= minLength -> {
                    messageSearch.text =
                        auxServicesImpl.capitalize("please, insert a name to anime or manga with more 2 characters")
                    messageSearch.setTextColor(Color.RED)
                }
                else -> {
                    val choiceUser = when (choice.text) {
                        "Anime" -> TypesRequest.Anime.type
                        "Manga" -> TypesRequest.Manga.type
                        else -> TypesRequest.Anime.type
                    }
                    /*
                        I've decided use anime by generic form, because both recyclerviews is the
                        same and their attributes in this case will the same.
                     */

                    progressBar.visibility = View.VISIBLE
                    val animeSearch = arrayListOf<Anime>()
                    animeAdapter = AnimeAdapter(animeSearch)
                    recyclerViewSearch.adapter = animeAdapter
                    recyclerViewSearch.layoutManager = GridLayoutManager(context, 2)

                    api.search(
                        choiceUser,
                        search.query.toString(),
                        limit
                    ).enqueue(object :
                        Callback<JsonObject> {
                        override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                            messageSearch.text =
                                auxServicesImpl.capitalize("not found this anime, try another please")
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
                                                anime.image_url =
                                                    animeFound.get("image_url").asString
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