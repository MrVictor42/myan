package com.victor.myan.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.victor.myan.adapter.MangaAdapter
import com.victor.myan.api.MangaApi
import com.victor.myan.databinding.FragmentTopMangaBinding
import com.victor.myan.enums.TypesRequest
import com.victor.myan.helper.JikanApiInstanceHelper
import com.victor.myan.model.Manga
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopMangaFragment : Fragment() {

    private lateinit var binding : FragmentTopMangaBinding
    private lateinit var mangaAdapter: MangaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopMangaBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mangaList = arrayListOf<Manga>()
        val recyclerViewTopManga = binding.recyclerViewTopManga

        recyclerViewTopManga.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        mangaAdapter = MangaAdapter(mangaList)
        recyclerViewTopManga.adapter = mangaAdapter

        val api = JikanApiInstanceHelper.getJikanApiInstance().create(MangaApi::class.java)
        api.getTopManga().enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if(response.isSuccessful) {
                    val mangaResponse = response.body()
                    mangaAdapter.manga.clear()
                    if(mangaResponse != null) {
                        val topManga : JsonArray? = mangaResponse.getAsJsonArray(TypesRequest.Top.type)
                        if (topManga != null) {
                            for(manga in 0 until topManga.size()) {
                                val mangaObject: JsonObject? = topManga.get(manga) as JsonObject?
                                if (mangaObject != null) {
                                    val mangaTop = Manga()

                                    mangaTop.title = mangaObject.get("title").asString
                                    mangaTop.mal_id = mangaObject.get("mal_id").asString
                                    mangaTop.image_url = mangaObject.get("image_url").asString
                                    mangaTop.start_date = mangaObject.get("start_date").asString

                                    if(mangaObject.get("volumes").toString() == "null") {
                                        mangaTop.volumes = 0
                                    } else {
                                        mangaTop.volumes = mangaObject.get("volumes").asInt
                                    }

                                    if(mangaObject.get("score").toString() == "null") {
                                        mangaTop.score = 0.0
                                    } else {
                                        mangaTop.score = mangaObject.get("score").asDouble
                                    }

                                    mangaAdapter.manga.add(mangaTop)
                                }
                            }
                            mangaAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        })
    }
}