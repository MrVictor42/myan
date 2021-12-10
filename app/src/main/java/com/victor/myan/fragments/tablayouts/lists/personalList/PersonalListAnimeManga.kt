package com.victor.myan.fragments.tablayouts.lists.personalList

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.adapter.PersonalListAnimeAdapter
import com.victor.myan.adapter.PersonalListMangaAdapter
import com.victor.myan.databinding.FragmentPersonalListAnimeMangaBinding
import com.victor.myan.model.Anime
import com.victor.myan.model.Manga
import com.victor.myan.viewmodel.PersonalListViewModel

class PersonalListAnimeManga : Fragment() {

    private lateinit var binding : FragmentPersonalListAnimeMangaBinding
    private lateinit var personalListAnimeAdapter : PersonalListAnimeAdapter
    private lateinit var personalListMangaAdapter: PersonalListMangaAdapter
    private val personalListViewModel by lazy {
        ViewModelProvider(this)[PersonalListViewModel::class.java]
    }

    companion object {
        fun newInstance(id : String, type : String): PersonalListAnimeManga {
            val personalListAnime = PersonalListAnimeManga()
            val args = Bundle()
            args.putString("ID", id)
            args.putString("type", type)
            personalListAnime.arguments = args
            return personalListAnime
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalListAnimeMangaBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val idList = arguments?.getString("ID")!!
        val type = arguments?.getString("type")!!
        val listRef = personalListViewModel.listRef.ref.child(idList)
        val shimmerLayout = binding.shimmerLayout
        val recyclerView = binding.recyclerView
        val btnRemove = binding.btnRemove

        when (type) {
            "anime" -> {
                val animeRef = listRef.ref.child("anime")
                val animeList: MutableList<Anime> = arrayListOf()

                animeRef.get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val result = task.result
                        if (result != null) {
                            if (result.exists()) {
                                result.let {
                                    result.children.map { snapshot ->
                                        animeList.add(snapshot.getValue(Anime::class.java)!!)
                                    }
                                }
                                personalListAnimeAdapter =
                                    PersonalListAnimeAdapter(btnRemove, animeRef)
                                personalListAnimeAdapter.setData(animeList)
                                recyclerView.layoutManager =
                                    GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                                recyclerView.adapter = personalListAnimeAdapter
                                shimmerLayout.visibility = View.GONE
                                recyclerView.visibility = View.VISIBLE
                            } else {
                                val emptyList = binding.emptyList
                                recyclerView.visibility = View.GONE
                                shimmerLayout.visibility = View.GONE
                                emptyList.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
            "manga" -> {
                val mangaRef = listRef.ref.child("manga")
                val mangaList : MutableList<Manga> = arrayListOf()

                mangaRef.get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val result = task.result
                        if (result != null) {
                            if (result.exists()) {
                                result.let {
                                    result.children.map { snapshot ->
                                        mangaList.add(snapshot.getValue(Manga::class.java)!!)
                                    }
                                }
                                personalListMangaAdapter = PersonalListMangaAdapter(btnRemove, mangaRef)
                                personalListMangaAdapter.setData(mangaList)
                                recyclerView.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                                recyclerView.adapter = personalListMangaAdapter
                                shimmerLayout.visibility = View.GONE
                                recyclerView.visibility = View.VISIBLE
                            } else {
                                val emptyList = binding.emptyList
                                recyclerView.visibility = View.GONE
                                shimmerLayout.visibility = View.GONE
                                emptyList.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }
    }
}