package com.victor.myan.fragments.tablayouts.listsDetail.personalList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.adapter.PersonalListAnimeAdapter
import com.victor.myan.databinding.FragmentPersonalListAnimeBinding
import com.victor.myan.model.Anime
import com.victor.myan.viewmodel.PersonalListViewModel

class PersonalListAnime : Fragment() {

    private lateinit var binding : FragmentPersonalListAnimeBinding
    private lateinit var personalListAnimeAdapter : PersonalListAnimeAdapter
    private val personalListViewModel by lazy {
        ViewModelProvider(this).get(PersonalListViewModel::class.java)
    }

    companion object {
        fun newInstance(id : String): PersonalListAnime {
            val personalListAnime = PersonalListAnime()
            val args = Bundle()
            args.putString("ID", id)
            personalListAnime.arguments = args
            return personalListAnime
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalListAnimeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val idList = arguments?.getString("ID")!!
        val listRef = personalListViewModel.listRef.ref.child(idList)
        val animeRef = listRef.ref.child("anime")
        val animeList : MutableList<Anime> = arrayListOf()
        val shimmerLayout = binding.shimmerLayout
        val recyclerView = binding.recyclerView.recyclerViewVertical

        shimmerLayout.startShimmer()
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
                        recyclerView.setHasFixedSize(true)
                        recyclerView.setItemViewCacheSize(6)
                        personalListAnimeAdapter = PersonalListAnimeAdapter()
                        personalListAnimeAdapter.submitList(animeList)
                        recyclerView.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                        recyclerView.adapter = personalListAnimeAdapter
                        shimmerLayout.stopShimmer()
                        shimmerLayout.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                    } else {
                        val emptyList = binding.emptyList

                        shimmerLayout.stopShimmer()
                        shimmerLayout.visibility = View.GONE
                        emptyList.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}