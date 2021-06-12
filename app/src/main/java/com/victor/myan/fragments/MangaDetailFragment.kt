package com.victor.myan.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.squareup.picasso.Picasso
import com.victor.myan.R
import com.victor.myan.api.AnimeApi
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
                    }
                }
            }
        }

        /*

                        animeYear.text = year
                        if(animeResponse.score.toString().isNullOrEmpty() || animeResponse.score == 0.0) {
                            animeScore.text = auxServicesHelper.capitalize(MessagesEnum.Undefined.message)
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
                                listGenres.add(animeResponse.genres[genre].name)
                            }
                            animeGenres.text = listGenres.toString()
                            listGenres.clear()
                        }

                        if(animeResponse.producers.isEmpty()) {
                            animeProducers.text = auxServicesHelper.capitalize(MessagesEnum.MissingProducers.message)
                        } else {
                            for(producer in animeResponse.producers.indices) {
                                listProducers.add(animeResponse.producers[producer].name)
                            }
                            animeProducers.text = listProducers.toString()
                            listProducers.clear()
                        }

                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            binding.animeSynopsis.justificationMode = Layout.JUSTIFICATION_MODE_INTER_WORD
                            animeSynopsis.text = animeResponse.synopsis
                        } else {
                            animeSynopsis.text = animeResponse.synopsis
                        }

                        animeVideo.addYouTubePlayerListener(object :
                            AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                if(animeResponse.trailer_url.isNullOrEmpty()) {
                                    Toast.makeText(
                                        context,
                                        auxServicesHelper.capitalize(
                                            MessagesEnum.MissingPreview.message
                                        ), Toast.LENGTH_LONG).show()
                                } else {
                                    val videoId = youtubeHelper.extractVideoIdFromUrl(animeResponse.trailer_url).toString()
                                    youTubePlayer.loadVideo(videoId, 0f)
                                }
                            }
                        })
                    }
                } else {
                    Toast.makeText(
                        context,
                        auxServicesHelper.capitalize(MessagesEnum.FailureLoadAnime.message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
         */
    }
}