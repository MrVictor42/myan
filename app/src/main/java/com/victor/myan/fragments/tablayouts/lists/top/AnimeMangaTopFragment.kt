package com.victor.myan.fragments.tablayouts.lists.top

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.adapter.AnimeAdapter
import com.victor.myan.adapter.MangaAdapter
import com.victor.myan.databinding.FragmentAnimeMangaTopBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.viewmodel.AnimeViewModel
import com.victor.myan.viewmodel.MangaViewModel

class AnimeMangaTopFragment : Fragment() {

    private lateinit var binding : FragmentAnimeMangaTopBinding
    private lateinit var animeAdapter: AnimeAdapter
    private lateinit var mangaAdapter: MangaAdapter
    private val animeViewModel by lazy {
        ViewModelProvider(this)[AnimeViewModel::class.java]
    }
    private val mangaViewModel by lazy {
        ViewModelProvider(this)[MangaViewModel::class.java]
    }

    companion object {
        fun newInstance(type : String): AnimeMangaTopFragment {
            val animeTopFragment = AnimeMangaTopFragment()
            val args = Bundle()
            args.putString("type", type)
            animeTopFragment.arguments = args
            return animeTopFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimeMangaTopBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val type = arguments?.getString("type")
        val recyclerView = binding.recyclerView
        val shimmerLayout = binding.shimmerLayout
        val errorOptions = binding.errorOptions.errorOptions
        val btnRefresh = binding.errorOptions.btnRefresh

        when(type) {
            "anime" -> {
                animeViewModel.getAnimeListTopApi()
                animeViewModel.animeListTop.observe(viewLifecycleOwner, { animeTop ->
                    when(animeTop) {
                        is ScreenStateHelper.Loading -> {

                        }
                        is ScreenStateHelper.Success -> {
                            if(animeTop.data != null) {
                                val animeTopList = animeTop.data
                                recyclerView.layoutManager =
                                    GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                                animeAdapter = AnimeAdapter()
                                animeAdapter.setData(animeTopList)
                                recyclerView.adapter = animeAdapter
                                shimmerLayout.visibility = View.GONE
                                recyclerView.visibility = View.VISIBLE
                            }
                        }
                        is ScreenStateHelper.Error -> {
                            errorOptions.visibility = View.VISIBLE
                            shimmerLayout.visibility = View.GONE

                            btnRefresh.setOnClickListener {
                                onViewCreated(view, savedInstanceState)

                                errorOptions.visibility = View.GONE
                            }
                        }
                        else -> {

                        }
                    }
                })
            }
            "manga" -> {
                mangaViewModel.getMangaListTopApi()
                mangaViewModel.mangaTopList.observe(viewLifecycleOwner, { mangaTop ->
                    when(mangaTop) {
                        is ScreenStateHelper.Loading -> {

                        }
                        is ScreenStateHelper.Success -> {
                            if(mangaTop.data != null) {
                                val mangaTopList = mangaTop.data
                                recyclerView.layoutManager =
                                    GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                                mangaAdapter = MangaAdapter()
                                mangaAdapter.setData(mangaTopList)
                                recyclerView.adapter = mangaAdapter
                                shimmerLayout.visibility = View.GONE
                                recyclerView.visibility = View.VISIBLE
                            }
                        }
                        is ScreenStateHelper.Error -> {
                            errorOptions.visibility = View.VISIBLE
                            shimmerLayout.visibility = View.GONE

                            btnRefresh.setOnClickListener {
                                onViewCreated(view, savedInstanceState)

                                errorOptions.visibility = View.GONE
                            }
                        }
                        else -> {

                        }
                    }
                })
            }
        }
    }
}