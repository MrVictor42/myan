package com.victor.myan.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import com.victor.myan.R
import com.victor.myan.api.MangaApi
import com.victor.myan.databinding.FragmentMangaDetailBinding
import com.victor.myan.enums.MangaStatusEnum
import com.victor.myan.enums.MessagesEnum
import com.victor.myan.enums.TypesEnum
import com.victor.myan.helper.AuxFunctionsHelper
import com.victor.myan.helper.JikanApiInstanceHelper
import com.victor.myan.screens.HomeFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MangaDetailFragment : Fragment() {

    private lateinit var binding: FragmentMangaDetailBinding
    private val auxServicesHelper = AuxFunctionsHelper()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMangaDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val homeFragment = HomeFragment.newInstance()
                val fragmentManager = fragmentManager
                fragmentManager?.beginTransaction()?.replace(R.id.content, homeFragment)
                    ?.addToBackStack(null)?.commit()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        val malID = arguments?.getString("mal_id")
        val year = arguments?.getString("year")

        val mangaImage = binding.mangaImage
        val mangaTitle = binding.mangaTitle
        val mangaStatus = binding.mangaStatus
        val mangaYear = binding.mangaYear
        val mangaVolume = binding.mangaVolume
        val mangaChapters = binding.mangaChapters
        val mangaScore = binding.mangaScore
        val mangaAuthors = binding.mangaAuthors
        val mangaAdaptations = binding.mangaAdaptations
        val mangaGenres = binding.mangaGenres
        val mangaSpinOff = binding.mangaSpinOff
        val mangaSynopsis = binding.mangaSynopsis

        val listGenres: MutableList<String> = mutableListOf()
        val listAuthors: MutableList<String> = mutableListOf()
        val listAdaptations: MutableList<String> = mutableListOf()
        val listSpinOff: MutableList<String> = mutableListOf()

        val api = JikanApiInstanceHelper.getJikanApiInstance().create(MangaApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<JsonObject> = api.getManga(malID.toString())
            withContext(Dispatchers.Main) {
                if (call.isSuccessful) {
                    val mangaResponse = call.body()
                    if (mangaResponse != null) {
                        Picasso.get().load(mangaResponse.get("image_url").asString).into(mangaImage)
                        mangaTitle.text = mangaResponse.get("title").asString
                        mangaStatus.text = mangaResponse.get("status").asString
                        mangaYear.text = year

                        when (mangaStatus.text) {
                            MangaStatusEnum.Publishing.status ->
                                mangaStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.green_light))
                            MangaStatusEnum.Finished.status ->
                                mangaStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                        }

                        if (mangaResponse.get("volumes").toString().isEmpty() ||
                            mangaResponse.get("volumes").toString() == "null"
                        ) {
                            mangaVolume.text =
                                auxServicesHelper.capitalize(MessagesEnum.Undefined.message)
                        } else {
                            mangaVolume.text = mangaResponse.get("volumes").asString
                        }

                        if (mangaResponse.get("chapters").toString().isEmpty() ||
                            mangaResponse.get("chapters").toString() == "null"
                        ) {
                            mangaChapters.text =
                                auxServicesHelper.capitalize(MessagesEnum.Undefined.message)
                        } else {
                            mangaChapters.text = mangaResponse.get("chapters").asString
                        }

                        if (mangaResponse.get("score").toString().isEmpty() ||
                            mangaResponse.get("score").toString() == "null"
                        ) {
                            mangaScore.text =
                                auxServicesHelper.capitalize(MessagesEnum.Undefined.message)
                        } else {
                            mangaScore.text = mangaResponse.get("score").asString
                        }

                        if (mangaResponse.get("authors").toString().isEmpty() ||
                            mangaResponse.get("authors").toString() == "null"
                        ) {
                            mangaAuthors.text =
                                auxServicesHelper.capitalize(MessagesEnum.MissingAuthors.message)
                        } else {
                            val authors : JsonArray? = mangaResponse.get("authors") as JsonArray?
                            if(authors != null) {
                                for(author in 0 until authors.size()) {
                                    val authorObject : JsonObject? = authors.get(author) as JsonObject?
                                    if(authorObject != null) {
                                        listAuthors.add(authorObject.get("name").asString)
                                    }
                                }
                                mangaAuthors.text = listAuthors.toString()
                                listAuthors.clear()
                            }
                        }

                        val related: JsonObject? =
                            mangaResponse.get(TypesEnum.Related.type) as JsonObject?
                        if (related != null) {
                            val adaptations: JsonArray? =
                                related.getAsJsonArray(TypesEnum.Adaptation.type)
                            if (adaptations != null) {
                                for (adaptation in 0 until adaptations.size()) {
                                    val adaptationObject: JsonObject? =
                                        adaptations.get(adaptation) as JsonObject?
                                    if (adaptationObject != null) {
                                        listAdaptations.add(adaptationObject.get("name").asString)
                                    }
                                }
                                mangaAdaptations.text = listAdaptations.toString()
                                listAdaptations.clear()
                            } else {
                                mangaAdaptations.text =
                                    auxServicesHelper.capitalize(MessagesEnum.MissingAdaptations.message)
                            }

                            val spinoffs: JsonArray? =
                                related.getAsJsonArray(TypesEnum.SpinOff.type)
                            if (spinoffs != null) {
                                for (spinoff in 0 until spinoffs.size()) {
                                    val sprinoffObject: JsonObject? =
                                        spinoffs.get(spinoff) as JsonObject?
                                    if (sprinoffObject != null) {
                                        listSpinOff.add(sprinoffObject.get("name").asString)
                                    }
                                }
                                mangaSpinOff.text = listSpinOff.toString()
                                listSpinOff.clear()
                            } else {
                                mangaSpinOff.text =
                                    auxServicesHelper.capitalize(MessagesEnum.MissingSpinOff.message)
                            }
                        }

                        if(mangaResponse.get("genres").toString().isEmpty() ||
                            mangaResponse.get("genres").toString() == "null") {
                            mangaGenres.text = auxServicesHelper.capitalize(MessagesEnum.MissingGenres.message)
                        } else {
                            val genres : JsonArray? = mangaResponse.get("genres") as JsonArray?
                            if(genres != null) {
                                for(genre in 0 until genres.size()) {
                                    val genreObject : JsonObject? = genres.get(genre) as JsonObject?
                                    if(genreObject != null) {
                                        listGenres.add(genreObject.get("name").asString)
                                    }
                                }
                                mangaGenres.text = listGenres.toString()
                                listGenres.clear()
                            }
                        }

                        mangaSynopsis.text = mangaResponse.get("synopsis").asString

                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            binding.mangaSynopsis.justificationMode = Layout.JUSTIFICATION_MODE_INTER_WORD
                            mangaSynopsis.text = mangaResponse.get("synopsis").asString
                        } else {
                            mangaSynopsis.text = mangaResponse.get("synopsis").asString
                        }
                    }
                }
            }
        }
    }
}