package com.victor.myan.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victor.myan.R
import com.victor.myan.adapter.CatalogAdapter
import com.victor.myan.databinding.FragmentCatalogBinding
import com.victor.myan.enums.AnimeGenreEnum

class CatalogFragment : Fragment() {

    private lateinit var binding : FragmentCatalogBinding
    private lateinit var catalogAdapter : CatalogAdapter

    companion object {
        fun newInstance(): CatalogFragment {
            val catalogFragment = CatalogFragment()
            val args = Bundle()
            catalogFragment.arguments = args
            return catalogFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatalogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val catalogList = arrayListOf<AnimeGenreEnum>()
        val catalogGenres = AnimeGenreEnum.values()
        val recyclerViewCatalog = binding.recyclerViewCatalog
        recyclerViewCatalog.layoutManager =
            LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)

        catalogAdapter = CatalogAdapter(catalogList)
        recyclerViewCatalog.adapter = catalogAdapter
        recyclerViewCatalog.layoutManager = GridLayoutManager(context, 2)

        catalogAdapter.animeGenreEnum.clear()
        catalogGenres.forEach {
            catalogAdapter.animeGenreEnum.add(it)
            catalogAdapter.notifyDataSetChanged()
        }
    }
}