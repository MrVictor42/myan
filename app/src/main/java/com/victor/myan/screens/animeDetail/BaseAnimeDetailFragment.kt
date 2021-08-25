package com.victor.myan.screens.animeDetail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import com.victor.myan.R
import com.victor.myan.adapter.ViewPagerAnimeAdapter
import com.victor.myan.api.AnimeApi
import com.victor.myan.databinding.FragmentBaseAnimeDetailBinding
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.model.Picture
import com.victor.myan.screens.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BaseAnimeDetailFragment : Fragment() {

    private lateinit var binding : FragmentBaseAnimeDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseAnimeDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getString("mal_id").toString()
        val year = arguments?.getString("year").toString()
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager2
        val sizePager = 3
        val listPictures : MutableList<Picture> = mutableListOf()
        val carouselView = binding.carouselView.carouselViewCarousel
        val adapter = ViewPagerAnimeAdapter(parentFragmentManager, lifecycle, malID, year, sizePager)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager){tab, position ->
            when(position) {
                0 -> tab.text = "Overview"
                1 -> tab.text = "Characters"
                2 -> tab.text = "Recommendation"
            }
        }.attach()


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

        val animeApi = JikanApiInstance.getJikanApiInstance().create(AnimeApi::class.java)

        animeApi.getPictures(arguments?.getString("mal_id").toString()).enqueue(object :
            Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

            }

            @SuppressLint("InflateParams")
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val picturesResponse = response.body()
                    if(picturesResponse != null) {
                        val picturesArray: JsonArray? = picturesResponse.getAsJsonArray("pictures")
                        if (picturesArray != null) {
                            for (pictures in 0 until picturesArray.size()) {
                                val pictureObject: JsonObject? =
                                    picturesArray.get(pictures) as JsonObject?
                                if (pictureObject != null) {
                                    val picture = Picture()

                                    picture.large = pictureObject.get("large").asString
                                    listPictures.add(picture)
                                }
                            }
                            for (pictures in 0 until listPictures.size) {
                                carouselView.setViewListener { position ->
                                    val viewListener = layoutInflater.inflate(
                                        R.layout.fragment_carousel_custom,
                                        null
                                    )

                                    val animeImage =
                                        viewListener.findViewById<ImageView>(R.id.anime_image_carousel)
                                    Picasso.get().load(listPictures[position].large).fit()
                                        .into(animeImage)
                                    viewListener
                                }
                            }
                            carouselView.pageCount = listPictures.size
                        }
                    }
                }
            }
        })
    }
}