package com.victor.myan.fragments.tablayouts.lists.personalList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.adapter.PersonalListMangaAdapter
import com.victor.myan.databinding.FragmentPersonalListMangaBinding
import com.victor.myan.model.Manga
import com.victor.myan.viewmodel.PersonalListViewModel

class PersonalListManga : Fragment() {

    private lateinit var binding : FragmentPersonalListMangaBinding
    private lateinit var personalListMangaAdapter: PersonalListMangaAdapter
    private val personalListViewModel by lazy {
        ViewModelProvider(this)[PersonalListViewModel::class.java]
    }

    companion object {
        fun newInstance(id : String): PersonalListManga {
            val personalListManga = PersonalListManga()
            val args = Bundle()
            args.putString("ID", id)
            personalListManga.arguments = args
            return personalListManga
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalListMangaBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val idList = arguments?.getString("ID")!!
        val listRef = personalListViewModel.listRef.ref.child(idList)
        val mangaRef = listRef.ref.child("manga")
        val mangaList : MutableList<Manga> = arrayListOf()
        val shimmerLayout = binding.shimmerLayout
        val recyclerView = binding.recyclerView
        val btnRemove = binding.btnRemove

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