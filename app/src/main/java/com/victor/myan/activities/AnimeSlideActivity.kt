package com.victor.myan.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.victor.myan.R
import com.victor.myan.adapter.AnimeSlideAdapter
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.api.JikanApiServices
import com.victor.myan.databinding.ActivitySlidesHomeBinding
import com.victor.myan.enums.Type
import com.victor.myan.model.Anime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.awaitResponse

class AnimeSlideActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySlidesHomeBinding
    private lateinit var animeSlideAdapter: AnimeSlideAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySlidesHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val api = JikanApiInstance.getJikanApiInstance().create(JikanApiServices::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val call: Call<JsonObject> = api.getTopAiring()
            val response = call.awaitResponse()

            withContext(Dispatchers.Main) {
                if(response.isSuccessful) {
                    val animeResponse = response.body()
                    Log.e("Response: ", animeResponse.toString())
                    if(animeResponse != null) {
                        animeSlideAdapter.animeList.clear()
                        val top: JsonArray? = animeResponse.getAsJsonArray("top")
                        if(top != null) {
                            for(anime in 0 until top.size()) {
                                val animeObject: JsonObject? = top.get(anime) as JsonObject?
                                val topAiring = Anime()
                                if(animeObject != null) {
                                    topAiring.image_url = animeObject.get("image_url").asString
                                    Log.e("Image: ", topAiring.image_url)
                                    animeSlideAdapter.animeList.add(topAiring)
                                }
                            }
                            animeSlideAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        }
    }
}