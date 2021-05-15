package com.victor.myan.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.google.gson.JsonObject
import com.victor.myan.R
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.api.JikanApiServices
import com.victor.myan.databinding.FragmentAnimeDetailBinding
import com.victor.myan.model.Anime
import com.victor.myan.services.impl.AuxServicesImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import retrofit2.awaitResponse

class AnimeDetailFragment : Fragment() {

    private var _binding: FragmentAnimeDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val homeFragment = HomeFragment.newInstance()
                val fragmentManager = fragmentManager
                fragmentManager?.
                    beginTransaction()?.
                    replace(R.id.content, homeFragment)
                    ?.addToBackStack(null)
                    ?.commit()
            }
        }

        val listGenres: MutableList<String> = mutableListOf()
        val listProducers: MutableList<String> = mutableListOf()

        val animeTrailer = binding.playVideo
        val animeTitle = binding.animeTitle
        val animeStatus = binding.animeStatus
        val animeYear = binding.animeYear
        val animeScoreTextView = binding.animeScoreTextView
        val animeScore = binding.animeScore
        val animeEpisodesTextView = binding.animeEpisodesTextView
        val animeEpisodes = binding.animeEpisodes
        val animeGenresTextView = binding.animeGenresTextView
        val animeGenres = binding.animeGenres
        val animeProducersTextView = binding.animeProducersTextView
        val animeProducers = binding.animeProducers
        val animeSynopsis = binding.animeSynopsis

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        val api = JikanApiInstance.getJikanApiInstance().create(JikanApiServices::class.java)
        val auxServicesImpl = AuxServicesImpl()
        val malID = arguments?.getString("mal_id")
        val year = arguments?.getString("airing_start")

        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<Anime> = api.getAnime(malID.toString())
            withContext(Dispatchers.Main) {
                if (call.isSuccessful) {
                    val animeResponse = call.body()
                    if (animeResponse != null) {
                        animeTitle.text = animeResponse.title
                        animeStatus.text = animeResponse.status
                        if(animeStatus.text == "Currently Airing") {
                            animeStatus.setTextColor(resources.getColor(R.color.green_light))
                        } else {
                            animeStatus.setTextColor(resources.getColor(R.color.red))
                        }
                        animeYear.text = year
                        if(animeResponse.score.toString() == "") {
                            animeScoreTextView.isInvisible = true
                            animeScore.isInvisible = true
                        } else {
                            animeScore.text = animeResponse.score.toString()
                        }
                        if(animeResponse.episodes.toString() == "" || animeResponse.episodes == 0) {
                            animeEpisodesTextView.isInvisible = true
                            animeEpisodes.isInvisible = true
                        } else {
                            animeEpisodes.text = animeResponse.episodes.toString()
                        }

                        if(animeResponse.genres.isEmpty()) {
                            animeGenresTextView.isInvisible = true
                            animeGenres.isVisible = true
                        } else {
                            for(genre in animeResponse.genres.indices) {
                                listGenres.add(animeResponse.genres.get(genre).name)
                            }
                            animeGenres.text = listGenres.toString()
                            listGenres.clear()
                        }

                        if(animeResponse.producers.isEmpty()) {
                            animeProducersTextView.isInvisible = true
                            animeProducers.isInvisible = true
                        } else {
                            for(producer in animeResponse.producers.indices) {
                                listProducers.add(animeResponse.producers.get(producer).name)
                            }
                            animeProducers.text = listProducers.toString()
                            listProducers.clear()
                        }

                        animeSynopsis.text = animeResponse.synopsis
                    }
                } else {
                    Toast.makeText(
                        context,
                        auxServicesImpl.capitalize("not was possible load this anime now, try again later"),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}