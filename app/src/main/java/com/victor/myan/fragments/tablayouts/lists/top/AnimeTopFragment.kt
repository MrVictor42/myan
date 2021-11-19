package com.victor.myan.fragments.tablayouts.lists.top

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.adapter.AnimeAdapter
import com.victor.myan.databinding.FragmentAnimeTopBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.viewmodel.AnimeViewModel

class AnimeTopFragment : Fragment() {

    private lateinit var binding : FragmentAnimeTopBinding
    private lateinit var animeAdapter: AnimeAdapter
    private val animeViewModel by lazy {
        ViewModelProvider(this)[AnimeViewModel::class.java]
    }

    companion object {
        fun newInstance(): AnimeTopFragment {
            val animeTopFragment = AnimeTopFragment()
            val args = Bundle()
            animeTopFragment.arguments = args
            return animeTopFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimeTopBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val animeTopRecyclerView = binding.recyclerView
        val shimmerLayout = binding.shimmerLayout

        animeViewModel.getAnimeListTopApi()
        animeViewModel.animeListTop.observe(viewLifecycleOwner, { animeTop ->
            when(animeTop) {
                is ScreenStateHelper.Loading -> {

                }
                is ScreenStateHelper.Success -> {
                    if(animeTop.data != null) {
                        val animeTopList = animeTop.data
                        animeTopRecyclerView.layoutManager =
                            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                        animeAdapter = AnimeAdapter()
                        animeAdapter.setData(animeTopList)
                        animeTopRecyclerView.adapter = animeAdapter
                        shimmerLayout.visibility = View.GONE
                        animeTopRecyclerView.visibility = View.VISIBLE
                    }
                }
                is ScreenStateHelper.Error -> {

                }
                else -> {

                }
            }
        })
    }
}