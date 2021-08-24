package com.victor.myan.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.victor.myan.R
import com.victor.myan.databinding.FragmentCarouselBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Anime
import com.victor.myan.screens.animeDetail.BaseAnimeDetailFragment
import com.victor.myan.viewmodel.AnimeListCarouselViewModel

class CarouselFragment : Fragment() {

    private lateinit var binding : FragmentCarouselBinding
    private val viewModel by lazy { ViewModelProvider(this).get(AnimeListCarouselViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCarouselBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.animeListCarouselLiveData.observe(viewLifecycleOwner, { state ->
            processAnimeListCarouselResponse(state)
        })
    }

    @SuppressLint("InflateParams")
    private fun processAnimeListCarouselResponse(state: ScreenStateHelper<List<Anime>?>?) {
        val progressBar = binding.progressBar
        val carouselView = binding.carouselView

        when(state) {
            is ScreenStateHelper.Loading -> {
                progressBar.visibility = View.VISIBLE
            }
            is ScreenStateHelper.Success -> {
                if(state.data != null) {
                    val animeList = state.data
                    for(aux in animeList.indices) {
                        carouselView.setViewListener { position ->
                            val viewCarousel = layoutInflater.inflate(R.layout.fragment_carousel_anime_list, null)
                            val animeTitle = viewCarousel.findViewById<TextView>(R.id.anime_title_carousel)
                            val animeImage = viewCarousel.findViewById<ImageView>(R.id.anime_image_carousel)

                            Glide.with(viewCarousel.context).load(animeList[position].image_url).into(animeImage)
                            animeTitle.text = animeList[position].title

                            viewCarousel
                        }

                        carouselView.setImageClickListener { position ->
                            val fragment = BaseAnimeDetailFragment()
                            val fragmentManager = activity?.supportFragmentManager

                            val bundle = Bundle()
                            bundle.putString("mal_id", animeList[position].mal_id)

                            fragment.arguments = bundle

                            val transaction = fragmentManager?.beginTransaction()?.replace(R.id.content, fragment)
                            transaction?.commit()
                            fragmentManager?.beginTransaction()?.commit()
                        }
                    }
                    progressBar.visibility = View.GONE
                    carouselView.pageCount = animeList.size
                }
            }
            is ScreenStateHelper.Error -> {
                progressBar.visibility = View.VISIBLE
                val view = progressBar.rootView
                Snackbar.make(view,
                    "Connection with internet not found or internal error... Try again later",
                    Snackbar.LENGTH_LONG).show()
            }
        }
    }
}