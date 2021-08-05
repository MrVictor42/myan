package com.victor.myan.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.victor.myan.adapter.GenresAdapter
import com.victor.myan.databinding.FragmentCategoriesListBinding
import com.victor.myan.model.Category
import com.victor.myan.model.Genre

class GenresListFragment : Fragment() {

    private lateinit var binding : FragmentCategoriesListBinding
    private lateinit var genresAdapter: GenresAdapter

    companion object {
        fun newInstance(): GenresListFragment {
            val genreListFragment = GenresListFragment()
            val args = Bundle()
            genreListFragment.arguments = args
            return genreListFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val genreList = arrayListOf<Genre>()
        val recyclerViewCatalog = binding.recyclerViewCatalog
        recyclerViewCatalog.layoutManager =
            LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)

        genresAdapter = GenresAdapter(genreList)
        recyclerViewCatalog.adapter = genresAdapter
        recyclerViewCatalog.layoutManager = GridLayoutManager(context, 2)

        genresAdapter.genres.clear()

        FirebaseFirestore.getInstance().collection("genres")
            .addSnapshotListener {
                snapshot, exception -> exception?.let {
            return@addSnapshotListener
        }
            snapshot?.let {
                for(doc in snapshot) {
                    val genre = Genre()

                    genre.name = doc.get("name").toString()
                    genre.image = doc.get("image").toString()
                    genre.mal_id = Integer.parseInt(doc.get("mal_id").toString())
//                    val category = Category()
//
//                    category.type = doc.get("type").toString()
//                    category.image = doc.get("image").toString()
//                    category.genre = Integer.parseInt(doc.get("genre").toString())
//                    categoriesAdapter.categories.add(category)
                }
                genresAdapter.notifyDataSetChanged()
            }
        }
    }
}