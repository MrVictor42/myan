package com.victor.myan.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.victor.myan.adapter.GenreAdapter
import com.victor.myan.databinding.FragmentGenreBinding
import com.victor.myan.model.Genre

class GenreFragment : Fragment() {

    private lateinit var binding : FragmentGenreBinding
    private lateinit var genreAdapter: GenreAdapter

    companion object {
        fun newInstance(): GenreFragment {
            val genreListFragment = GenreFragment()
            val args = Bundle()
            genreListFragment.arguments = args
            return genreListFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGenreBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val genreList : MutableList<Genre> = arrayListOf()
        val recyclerViewGenre = binding.recyclerView
        val shimmerLayout = binding.shimmerLayout

        FirebaseFirestore
        .getInstance()
        .collection("genres")
        .addSnapshotListener { snapshot, exception ->
            exception?.let {
                return@addSnapshotListener
            }
            snapshot?.let {
                for (doc in snapshot) {
                    val genre = Genre()

                    genre.name = doc.get("name").toString()
                    genre.imageURL = doc.get("image").toString()
                    genre.malID = Integer.parseInt(doc.get("mal_id").toString())
                    genreList.add(genre)
                }
                recyclerViewGenre.layoutManager =
                    GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                genreAdapter = GenreAdapter()
                genreAdapter.setData(genreList)
                recyclerViewGenre.adapter = genreAdapter
                shimmerLayout.visibility = View.GONE
                recyclerViewGenre.visibility = View.VISIBLE
            }
        }
    }
}