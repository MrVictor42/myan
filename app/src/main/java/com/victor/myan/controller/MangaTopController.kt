package com.victor.myan.controller

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.victor.myan.R
import com.victor.myan.adapter.MangaAdapter
import com.victor.myan.helper.JikanApiInstanceHelper
import com.victor.myan.api.MangaApi
import com.victor.myan.enums.TypesRequest
import com.victor.myan.model.Manga
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MangaTopController {

    private lateinit var mangaAdapter: MangaAdapter

    fun getTopManga(view : View) {

        val mangaList = arrayListOf<Manga>()
        val recyclerViewTopManga = view.findViewById<RecyclerView>(R.id.recyclerViewTopManga)

        recyclerViewTopManga.layoutManager =
            LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
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