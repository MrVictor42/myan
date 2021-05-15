package com.victor.myan.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.google.gson.JsonObject
import com.victor.myan.R
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.api.JikanApiServices
import com.victor.myan.databinding.FragmentAnimeDetailBinding
import com.victor.myan.model.Anime
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

        val animeTrailer = binding.playVideo
        val animeTitle = binding.animeTitle
        val animeStatus = binding.animeStatus
        val animeYear = binding.animeYear

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        val api = JikanApiInstance.getJikanApiInstance().create(JikanApiServices::class.java)
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
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}