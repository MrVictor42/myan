package com.victor.myan.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.squareup.picasso.Picasso
import com.victor.myan.R
import com.victor.myan.api.AnimeApi
import com.victor.myan.helper.JikanApiInstanceHelper
import com.victor.myan.databinding.FragmentAnimeDetailBinding
import com.victor.myan.helper.YoutubeHelper
import com.victor.myan.model.Anime
import com.victor.myan.helper.AuxFunctionsHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import android.graphics.drawable.Drawable

import androidx.palette.graphics.Palette

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette.PaletteAsyncListener
import androidx.palette.graphics.Target.VIBRANT
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.SimpleTarget
import com.github.florent37.picassopalette.PicassoPalette
import com.github.florent37.picassopalette.PicassoPalette.Profile.VIBRANT
import com.github.florent37.picassopalette.PicassoPalette.Profile.VIBRANT_LIGHT
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.RequestCreator
import java.lang.Exception
import com.squareup.picasso.Target as Target


class AnimeDetailFragment : Fragment() {

    private lateinit var binding : FragmentAnimeDetailBinding
    private val auxServicesHelper = AuxFunctionsHelper()
    private val youtubeHelper = YoutubeHelper()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimeDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val homeFragment = HomeFragment.newInstance()
                val fragmentManager = activity?.supportFragmentManager
                fragmentManager?.popBackStack()
                fragmentManager?.
                beginTransaction()?.
                replace(R.id.content, homeFragment)?.addToBackStack(null)?.commit()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        val malID = arguments?.getString("mal_id")
        val year = arguments?.getString("year")
        val listGenres: MutableList<String> = mutableListOf()
        val listProducers: MutableList<String> = mutableListOf()
        val animeVideo = binding.youtubePlayerView
        lifecycle.addObserver(animeVideo)
        val animeTitle = binding.animeTitle
//        val animeStatus = binding.animeStatus
//        val animeYear = binding.animeYear
        val animeScore = binding.animeScore
//        val animeEpisodes = binding.animeEpisodes
        val animeImage = binding.animeImage
        val backgroundTop = binding.backgroundTop
//        val animeGenres = binding.animeGenres
//        val animeProducers = binding.animeProducers
//        val animeSynopsis = binding.animeSynopsis
        val animePopularity = binding.animePopularity
        val animeMembers = binding.animeMembers
        val animeFavorites = binding.animeFavorites
        val target : Target
        val api = JikanApiInstanceHelper.getJikanApiInstance().create(AnimeApi::class.java)
        val toolbar = binding.toolbar

        toolbar.toolbar.title = "Home"
        toolbar.toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        toolbar.toolbar.setNavigationOnClickListener {
            val homeFragment = HomeFragment()
            (view.context as FragmentActivity)
                .supportFragmentManager
                .beginTransaction()
                .remove(this)
                .replace(R.id.content, homeFragment)
                .addToBackStack(null)
                .commit()
        }

        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<Anime> = api.getAnime(malID.toString())
            withContext(Dispatchers.Main) {
                if (call.isSuccessful) {
                    val animeResponse = call.body()
                    if (animeResponse != null) {
                        animeTitle.text = animeResponse.title
//                        animeStatus.text = animeResponse.status
                        animePopularity.text = animeResponse.popularity.toString()
                        animeMembers.text = animeResponse.members.toString()
                        animeFavorites.text = animeResponse.favorites.toString()
                        Glide.with(this@AnimeDetailFragment).load(animeResponse.image_url).listener(object : RequestListener<Drawable>{
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: com.bumptech.glide.request.target.Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                Log.d("TAG Anime Detail", "Image not working")
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: com.bumptech.glide.request.target.Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                Palette.from(resource!!.toBitmap()).generate() { palette ->
                                    palette?.let {
                                        val color = it.darkVibrantSwatch?.rgb?:0
                                        backgroundTop.setBackgroundColor(color)
                                    }
                                }
                                return false
                            }
                        }).into(animeImage)


//                        when(animeStatus.text) {
//                            "Currently Airing" ->
//                                animeStatus.setTextColor(
//                                    ContextCompat.getColor(requireContext(), R.color.green_light)
//                                )
//                            "Not yet aired" ->
//                                animeStatus.setTextColor(
//                                    ContextCompat.getColor(requireContext(), R.color.dark_blue)
//                                )
//                            "Finished Airing" ->
//                                animeStatus.setTextColor(
//                                    ContextCompat.getColor(requireContext(), R.color.red)
//                                )
//                        }
//
//                        animeYear.text = year

                        if(animeResponse.score.toString().isNullOrEmpty() || animeResponse.score == 0.0) {
                            animeScore.text = "─"
                        } else {
                            animeScore.text = animeResponse.score.toString()
                        }

//                        if(animeResponse.episodes.toString() == "" || animeResponse.episodes == 0) {
//                            animeEpisodes.text = "─"
//                        } else {
//                            animeEpisodes.text = animeResponse.episodes.toString()
//                        }
//
//                        if(animeResponse.genres.isEmpty()) {
//                            animeGenres.text = "─"
//                        } else {
//                            for(genre in animeResponse.genres.indices) {
//                                listGenres.add(animeResponse.genres[genre].name)
//                            }
//                            animeGenres.text = listGenres.toString()
//                            listGenres.clear()
//                        }
//
//                        if(animeResponse.producers.isEmpty()) {
//                            animeProducers.text =
//                                auxServicesHelper.capitalize(
//                                    "not found the producers for this anime"
//                                )
//                        } else {
//                            for(producer in animeResponse.producers.indices) {
//                                listProducers.add(animeResponse.producers[producer].name)
//                            }
//                            animeProducers.text = listProducers.toString()
//                            listProducers.clear()
//                        }
//
//                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                            binding.animeSynopsis.justificationMode =
//                                Layout.JUSTIFICATION_MODE_INTER_WORD
//                            animeSynopsis.text = animeResponse.synopsis
//                        } else {
//                            animeSynopsis.text = animeResponse.synopsis
//                        }

                        animeVideo.addYouTubePlayerListener(object :
                            AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                if(animeResponse.trailer_url.isNullOrEmpty()) {
                                    Toast.makeText(
                                        context,
                                        auxServicesHelper.capitalize(
                                            "this anime doesn't have a preview yet"
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
                        auxServicesHelper.capitalize("not was possible load this now, try again later"),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}