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
import android.widget.ImageView
import androidx.palette.graphics.Palette.PaletteAsyncListener
import com.bumptech.glide.request.target.SimpleTarget
import com.github.florent37.picassopalette.PicassoPalette
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
                        Picasso.get().load(animeResponse.image_url).fit().into(animeImage)
                        

//                        , object : Target
//                             {
//                            override fun onBitmapLoaded(bitmap: Bitmap?, from: LoadedFrom?) {
//                                                                Palette.from(bitmap!!).generate { palette ->
//                                                                    val defaultValue = 0x000000
//                                                                    val vibrant =
//                                                                        palette!!.getVibrantColor(
//                                                                            defaultValue
//                                                                        )
//                                                                    val vibrantLight =
//                                                                        palette.getLightVibrantColor(
//                                                                            defaultValue
//                                                                        )
//                                                                    val vibrantDark =
//                                                                        palette.getDarkVibrantColor(
//                                                                            defaultValue
//                                                                        )
//                                                                    val muted =
//                                                                        palette.getMutedColor(
//                                                                            defaultValue
//                                                                        )
//                                                                    val mutedLight =
//                                                                        palette.getLightMutedColor(
//                                                                            defaultValue
//                                                                        )
//                                                                    val mutedDark =
//                                                                        palette.getDarkMutedColor(
//                                                                            defaultValue
//                                                                        )
//                                                                    backgroundTop.setBackgroundColor(
//                                                                        Color.GREEN
//                                                                    )
//                                                                }
//                            }
//
//                            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
//                                TODO("Not yet implemented")
//                            }
//
//                            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
//                                TODO("Not yet implemented")
//                            }
//
//
//
//                        })
//                        Picasso.get().load(animeResponse.image_url).fit().into(object : Target() {
//                            fun onBitmapLoaded(bitmap: Bitmap?, from: LoadedFrom?) {
//                                Palette.from(bitmap!!).generate { palette ->
//                                    val defaultValue = 0x000000
//                                    val vibrant = palette!!.getVibrantColor(defaultValue)
//                                    val vibrantLight = palette.getLightVibrantColor(defaultValue)
//                                    val vibrantDark = palette.getDarkVibrantColor(defaultValue)
//                                    val muted = palette.getMutedColor(defaultValue)
//                                    val mutedLight = palette.getLightMutedColor(defaultValue)
//                                    val mutedDark = palette.getDarkMutedColor(defaultValue)
//                                    backgroundTop.setBackgroundColor(vibrant)
//                                }
//                            }
//                        })

//                        Picasso.get().load(animeResponse.image_url).fit().into(animeImage,
//                            object : Target() {
//                                fun onBitmapLoaded(bitmap: Bitmap?, from: LoadedFrom?) {}
//                                fun onBitmapFailed(errorDrawable: Drawable?) {}
//                                fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
//                            })



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