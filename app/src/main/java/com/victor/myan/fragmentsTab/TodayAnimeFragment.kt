package com.victor.myan.fragmentsTab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.adapter.TodayAnimeAdapter
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.databinding.FragmentTodayAnimeBinding
import com.victor.myan.interfaces.JikanApiServices
import com.victor.myan.model.Anime
import kotlinx.coroutines.*

class TodayAnimeFragment : Fragment() {

    private lateinit var todayAnimeAdapter: TodayAnimeAdapter
    private lateinit var binding: FragmentTodayAnimeBinding

    companion object {
        fun newInstance(): TodayAnimeFragment {
            val todayAnimeFragment = TodayAnimeFragment()
            val args = Bundle()
            todayAnimeFragment.arguments = args
            return todayAnimeFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodayAnimeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val api = JikanApiInstance.getJikanApiInstance().create(JikanApiServices::class.java)
        val recyclerView = binding.recyclerTodayAnime
        val animeList = arrayListOf<Anime>()

        todayAnimeAdapter = TodayAnimeAdapter(animeList)
        recyclerView.adapter = todayAnimeAdapter
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        CoroutineScope(Dispatchers.IO).launch {
            val response = api.getTodayAnime()

            withContext(Dispatchers.Main) {
                if(response.isSuccessful) {
                    val anime = response.body()?.anime
                    if(anime != null) {
                        todayAnimeAdapter.items.clear()
                        for(aux in 0 until anime.count()) {
                            todayAnimeAdapter.items.add(anime[aux])
                        }
                        todayAnimeAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}