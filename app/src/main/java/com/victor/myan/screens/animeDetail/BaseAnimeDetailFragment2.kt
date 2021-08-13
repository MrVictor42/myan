package com.victor.myan.screens.animeDetail

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.victor.myan.R
import com.victor.myan.api.AnimeApi
import com.victor.myan.helper.JikanApiInstanceHelper
import com.victor.myan.databinding.FragmentAnimeDetailBinding
import com.victor.myan.helper.YoutubeHelper
import com.victor.myan.model.Anime
import com.victor.myan.helper.AuxFunctionsHelper
import retrofit2.Response
import com.squareup.picasso.Picasso
import com.victor.myan.adapter.AnimeAdapter
import com.victor.myan.adapter.CharactersAdapter
import com.victor.myan.api.StaffApi
import com.victor.myan.enums.StatusEnum
import com.victor.myan.model.Character
import com.victor.myan.screens.HomeFragment
import retrofit2.Call
import retrofit2.Callback

class BaseAnimeDetailFragment2 : Fragment() {

    private lateinit var binding : FragmentAnimeDetailBinding
    private lateinit var characterAdapter : CharactersAdapter
    private lateinit var animeAdapter: AnimeAdapter
    private val auxServicesHelper = AuxFunctionsHelper()
    private val youtubeHelper = YoutubeHelper()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimeDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val homeFragment = HomeFragment.newInstance()
                val fragmentManager = activity?.supportFragmentManager
                fragmentManager?.popBackStack()
                fragmentManager?.beginTransaction()?.replace(R.id.content, homeFragment)
                    ?.addToBackStack(null)?.commit()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        val malID = arguments?.getString("mal_id")
        val year = arguments?.getString("year")
        var listGenres = ""
        var listLicensors = ""
        var listStudios = ""
        val animeList = arrayListOf<Anime>()
        val animeVideo = binding.youtubePlayerView
        lifecycle.addObserver(animeVideo)
        val animeTitle = binding.animeTitle
        val animeStatus = binding.animeStatus
//        val animeScore = binding.animeScore
//        val animeImage = binding.animeImage
        val backgroundTop = binding.backgroundTop
        val animeGenres = binding.animeGenres
        val animeLicensors = binding.animeLicensors
        val animeStudios = binding.animeStudios
        val episodeDuration = binding.episodeDuration
        val recommendationRecyclerView = binding.recyclerRecommendations
//        val animePopularity = binding.animePopularity
//        val animeMembers = binding.animeMembers
//        val animeFavorites = binding.animeFavorites
        val expandableTextViewSynopsis = binding.expandableTextViewSynopsis.expandableTextView
        val expandableTextViewOpening = binding.expandableTextViewOpening.expandableTextView
        val expandableTextViewEnding = binding.expandableTextViewEnding.expandableTextView
        val typeYear = binding.typeYear
        val toolbar = binding.toolbar
        val backgroundImage = binding.imageBackgroundAnime
        val characterList = arrayListOf<Character>()
        val characterRecyclerView = binding.animeCharacter
        val animeApi = JikanApiInstanceHelper.getJikanApiInstance().create(AnimeApi::class.java)
        val staffApi = JikanApiInstanceHelper.getJikanApiInstance().create(StaffApi::class.java)

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

        characterRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        characterAdapter = CharactersAdapter(characterList)
        characterRecyclerView.adapter = characterAdapter

        recommendationRecyclerView.layoutManager =
            LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
        animeAdapter = AnimeAdapter(animeList)
        recommendationRecyclerView.adapter = animeAdapter
        animeApi.getAnime(malID.toString()).enqueue(object : Callback<Anime> {
            override fun onFailure(call: Call<Anime>, t: Throwable) {

            }

            override fun onResponse(call: Call<Anime>, response: Response<Anime>) {
                if (response.isSuccessful) {
                    val animeResponse = response.body()
                    if (animeResponse != null) {
                        Picasso.get().load(animeResponse.image_url).placeholder(R.drawable.placeholder).fit().into(backgroundImage)

//                        animeTitle.text = animeResponse.title
//                        toolbar.toolbar.title = animeResponse.title
//                        animePopularity.text = animeResponse.popularity.toString()
//                        animeMembers.text = animeResponse.members.toString()
//                        animeFavorites.text = animeResponse.favorites.toString()

                        if (animeResponse.title_synonyms.isEmpty()) {
                            toolbar.toolbar.subtitle = "─"
                        } else {
                            toolbar.toolbar.subtitle = animeResponse.title_synonyms.toString()
                        }


//                        Glide.with(this@AnimeDetailFragment).load(animeResponse.image_url)
//                            .listener(object : RequestListener<Drawable> {
//                                override fun onLoadFailed(
//                                    e: GlideException?,
//                                    model: Any?,
//                                    target: com.bumptech.glide.request.target.Target<Drawable>?,
//                                    isFirstResource: Boolean
//                                ): Boolean {
//                                    return false
//                                }
//
//                                override fun onResourceReady(
//                                    resource: Drawable?,
//                                    model: Any?,
//                                    target: com.bumptech.glide.request.target.Target<Drawable>?,
//                                    dataSource: DataSource?,
//                                    isFirstResource: Boolean
//                                ): Boolean {
//                                    Palette.from(resource!!.toBitmap()).generate { palette ->
//                                        palette?.let {
//                                            val color = it.darkVibrantSwatch?.rgb ?: 0
//                                            backgroundTop.setBackgroundColor(color)
//                                        }
//                                    }
//                                    return false
//                                }
//                            }
//                        ).into(animeImage)

                        val type = when (animeResponse.type) {
                            "" -> "─"
                            "null" -> "─"
                            else -> animeResponse.type
                        }

                        val typeYearConcat = "$type, $year"
                        typeYear.text = typeYearConcat

                        val episode = when (animeResponse.episodes.toString()) {
                            "null" -> "─"
                            "0" -> "─"
                            else -> animeResponse.episodes
                        }

                        val duration =
                            auxServicesHelper.formatDurationEpisode(type, animeResponse.duration)
                        val epiDuration = "$episode eps, $duration"

                        episodeDuration.text = epiDuration

                        when (animeResponse.status) {
                            "null" -> animeStatus.text = "─"
                            "" -> animeStatus.text = "─"

                            StatusEnum.CurrentlyAiring.status -> {
                                animeStatus.text = StatusEnum.CurrentlyAiring.status
                                animeStatus.setTextColor(
                                    ContextCompat.getColor(requireContext(), R.color.green_light)
                                )
                            }

                            StatusEnum.NotYetAired.status -> {
                                animeStatus.text = StatusEnum.NotYetAired.status
                                animeStatus.setTextColor(
                                    ContextCompat.getColor(requireContext(), R.color.dark_blue)
                                )
                            }

                            StatusEnum.FinishedAiring.status -> {
                                animeStatus.text = StatusEnum.FinishedAiring.status
                                animeStatus.setTextColor(
                                    ContextCompat.getColor(requireContext(), R.color.red)
                                )
                            }
                        }

//                        if (animeResponse.score.toString()
//                                .isNullOrEmpty() || animeResponse.score == 0.0
//                        ) {
//                            animeScore.text = "─"
//                        } else {
//                            animeScore.text = animeResponse.score.toString()
//                        }

                        if (animeResponse.genres.isEmpty()) {
                            // Nothing to do
                        } else {
                            for (genre in animeResponse.genres.indices) {

                                listGenres += animeResponse.genres[genre].name
                                if (genre < animeResponse.genres.size - 1) {
                                    listGenres += " • "
                                }
                            }
                            animeGenres.text = listGenres
                        }

                        animeVideo.addYouTubePlayerListener(object :
                            AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                if (animeResponse.trailer_url.isNullOrEmpty()) {
                                    Toast.makeText(
                                        context,
                                        auxServicesHelper.capitalize(
                                            "this anime doesn't have a preview yet"
                                        ), Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    val videoId =
                                        youtubeHelper.extractVideoIdFromUrl(animeResponse.trailer_url)
                                            .toString()
                                    youTubePlayer.loadVideo(videoId, 0f)
                                    youTubePlayer.pause()
                                }
                            }
                        })

                        expandableTextViewSynopsis.text = animeResponse.synopsis
                        expandableTextViewOpening.text =
                            animeResponse.opening_themes.toString().replace(",", "\n")
                                .replace("[", "").replace("]", "")
                        expandableTextViewEnding.text =
                            animeResponse.ending_themes.toString().replace(",", "\n")
                                .replace("[", "").replace("]", "")

                        staffApi.getCharactersStaff(animeResponse.mal_id).enqueue(object : Callback<JsonObject> {
                            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

                            }

                            @SuppressLint("NotifyDataSetChanged")
                            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                                if (response.isSuccessful) {
                                    val staffResponse = response.body()
                                    characterAdapter.character.clear()
                                    if (staffResponse != null) {
                                        val animeCharacters: JsonArray? =
                                            staffResponse.getAsJsonArray("characters")
                                        if (animeCharacters != null) {
                                            for (characters in 0 until animeCharacters.size()) {
                                                val characterObject: JsonObject? =
                                                    animeCharacters.get(characters) as JsonObject?
                                                if (characterObject != null) {
                                                    val character = Character()

                                                    character.mal_id =
                                                        characterObject.get("mal_id").asInt
                                                    character.image_url =
                                                        characterObject.get("image_url").asString
                                                    character.name =
                                                        characterObject.get("name").asString

                                                    characterAdapter.character.add(character)
                                                }
                                            }
                                            characterAdapter.notifyDataSetChanged()
                                        }
                                    }
                                }
                            }
                        })

                        if (animeResponse.licensors.isEmpty()) {
                            animeLicensors.text = "Unknown"
                        } else {
                            for (licensor in animeResponse.licensors.indices) {
                                listLicensors += animeResponse.licensors[licensor].name
                                if (licensor < animeResponse.licensors.size - 1) {
                                    listLicensors += "\n"
                                }
                            }
                            animeLicensors.text = listLicensors
                        }

                        if (animeResponse.studios.isEmpty()) {
                            animeStudios.text = "Unknown"
                        } else {
                            for (studio in animeResponse.studios.indices) {
                                listStudios += animeResponse.studios[studio].name
                                if (studio < animeResponse.studios.size - 1) {
                                    listStudios += "\n"
                                }
                            }
                            animeStudios.text = listStudios
                        }
                    }

                    animeApi.getRecommendations(animeResponse?.mal_id.toString()).enqueue(object : Callback<JsonObject> {
                        override fun onFailure(call: Call<JsonObject>, t: Throwable) {

                        }

                        override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                            if(response.isSuccessful) {
                                val animeRecommendationResponse = response.body()
                                animeAdapter.anime.clear()
                                if(animeRecommendationResponse != null) {
                                    val recommendationArray : JsonArray? = animeRecommendationResponse.getAsJsonArray("recommendations")
                                    if(recommendationArray != null) {
                                        for(recommendation in 0 until recommendationArray.size()) {
                                            val recommendationObject : JsonObject? = recommendationArray.get(recommendation) as JsonObject?
                                            if(recommendationObject != null) {
                                                animeApi.getAnime(recommendationObject.get("mal_id").asString).enqueue(object : Callback<Anime> {
                                                    override fun onFailure(call: Call<Anime>, t: Throwable) {

                                                    }

                                                    @SuppressLint("NotifyDataSetChanged")
                                                    override fun onResponse(call: Call<Anime>, response: Response<Anime>) {
                                                        if (response.isSuccessful) {
                                                            val animeResponseRecommendation = response.body()
                                                            if(animeResponseRecommendation != null) {
                                                                val animeRecommendation = Anime()

                                                                animeRecommendation.mal_id = animeResponseRecommendation.mal_id
                                                                animeRecommendation.image_url = animeResponseRecommendation.image_url
                                                                animeRecommendation.title = animeResponseRecommendation.title

                                                                if(animeResponseRecommendation.episodes.toString().isNullOrEmpty()) {
                                                                    animeRecommendation.episodes = 0
                                                                } else {
                                                                    animeRecommendation.episodes = animeResponseRecommendation.episodes
                                                                }

                                                                if(animeResponseRecommendation.premiered.isNullOrEmpty()) {
                                                                    animeRecommendation.premiered = ""
                                                                } else {
                                                                    animeRecommendation.premiered = auxServicesHelper.formatPremiered(animeResponseRecommendation.premiered)
                                                                }

                                                                if(animeResponseRecommendation.score.toString().isNullOrEmpty()) {
                                                                    animeRecommendation.score = 0.0
                                                                } else {
                                                                    animeRecommendation.score = animeResponseRecommendation.score
                                                                }

                                                                animeAdapter.anime.add(animeRecommendation)
                                                            }
                                                            animeAdapter.notifyDataSetChanged()
                                                        }
                                                    }
                                                })
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    })
                } else {
                    Toast.makeText(
                        context,
                        auxServicesHelper.capitalize("not was possible load this now, try again later"),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}