package com.victor.myan.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.squareup.picasso.Picasso
import com.victor.myan.R
import com.victor.myan.api.MangaApi
import com.victor.myan.databinding.FragmentMangaDetailBinding
import com.victor.myan.enums.MangaStatusEnum
import com.victor.myan.enums.MessagesEnum
import com.victor.myan.helper.AuxFunctionsHelper
import com.victor.myan.helper.JikanApiInstanceHelper
import com.victor.myan.model.Manga
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MangaDetailFragment : Fragment() {

    private lateinit var binding : FragmentMangaDetailBinding
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
                fragmentManager?.
                beginTransaction()?.
                replace(R.id.content, homeFragment)?.addToBackStack(null)?.commit()
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

        val listGenres : MutableList<String> = mutableListOf()
        val listAuthors : MutableList<String> = mutableListOf()
        val listAdaptations : MutableList<String> = mutableListOf()
        val listSpinOff : MutableList<String> = mutableListOf()

        val api = JikanApiInstanceHelper.getJikanApiInstance().create(MangaApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val call : Response<Manga> = api.getManga(malID.toString())
            withContext(Dispatchers.Main) {
                if(call.isSuccessful) {
                    val mangaResponse = call.body()
                    if(mangaResponse != null) {
                        Picasso.get().load(mangaResponse.image_url).into(mangaImage)
                        mangaTitle.text = mangaResponse.title
                        mangaStatus.text = mangaResponse.status
                        mangaYear.text = year

                        when(mangaStatus.text) {
                            MangaStatusEnum.Publishing.status ->
                                mangaStatus.setTextColor(resources.getColor(R.color.green_light))
                            MangaStatusEnum.Finished.status ->
                                mangaStatus.setTextColor(resources.getColor(R.color.red))
                        }

                        if(mangaResponse.volumes.toString().isNullOrEmpty() || mangaResponse.volumes == 0) {
                            mangaVolume.text = auxServicesHelper.capitalize(MessagesEnum.Undefined.message)
                        } else {
                            mangaVolume.text = mangaResponse.volumes.toString()
                        }

                        if(mangaResponse.chapters.toString().isNullOrEmpty() || mangaResponse.chapters == 0) {
                            mangaChapters.text = auxServicesHelper.capitalize(MessagesEnum.Undefined.message)
                        } else {
                            mangaChapters.text = mangaResponse.chapters.toString()
                        }

                        if(mangaResponse.score.toString().isNullOrEmpty() || mangaResponse.score == 0.0) {
                            mangaScore.text = auxServicesHelper.capitalize(MessagesEnum.Undefined.message)
                        } else {
                            mangaScore.text = mangaResponse.score.toString()
                        }

                        if(mangaResponse.authors.isNullOrEmpty()) {
                            mangaAuthors.text = auxServicesHelper.capitalize(MessagesEnum.MissingAuthors.message)
                        } else {
                            for(author in mangaResponse.authors.indices) {
                                listAuthors.add(mangaResponse.authors[author].name)
                            }
                            mangaAuthors.text = listAuthors.toString()
                            listAuthors.clear()
                        }

                        if(mangaResponse.adaptations.isNullOrEmpty()) {
                            mangaAdaptations.text = auxServicesHelper.capitalize(MessagesEnum.MissingAdaptations.message)
                        } else {
                            for(adaptation in mangaResponse.adaptations.indices) {
                                listAdaptations.add(mangaResponse.adaptations[adaptation].name)
                            }
                            mangaAdaptations.text = listAdaptations.toString()
                            listAdaptations.clear()
                        }

                        if(mangaResponse.genres.isNullOrEmpty()) {
                            mangaAdaptations.text = auxServicesHelper.capitalize(MessagesEnum.MissingGenres.message)
                        } else {
                            for(genre in mangaResponse.genres.indices) {
                                listGenres.add(mangaResponse.genres[genre].name)
                            }
                            mangaGenres.text = listGenres.toString()
                            listGenres.clear()
                        }

                        if(mangaResponse.spinOff.isNullOrEmpty()) {
                            mangaSpinOff.text = auxServicesHelper.capitalize(MessagesEnum.MissingSpinOff.message)
                        } else {
                            for(spinoff in mangaResponse.spinOff.indices) {
                                listSpinOff.add(mangaResponse.spinOff[spinoff].name)
                            }
                            mangaSpinOff.text = listSpinOff.toString()
                            listSpinOff.clear()
                        }

                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            binding.mangaSynopsis.justificationMode = Layout.JUSTIFICATION_MODE_INTER_WORD
                            mangaSynopsis.text = mangaResponse.synopsis
                        } else {
                            mangaSynopsis.text = mangaResponse.synopsis
                        }

                    }
                } else {
                    Toast.makeText(
                        context,
                        auxServicesHelper.capitalize(MessagesEnum.FailureLoad.message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}