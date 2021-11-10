package com.victor.myan.fragments.tablayouts.listsDetail.top

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.adapter.AnimeHorizontalAdapter
import com.victor.myan.databinding.FragmentAnimeTopBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.viewmodel.AnimeViewModel

class AnimeTopFragment : Fragment() {

    private lateinit var binding : FragmentAnimeTopBinding
    private lateinit var animeHorizontalAdapter: AnimeHorizontalAdapter
    private val animeViewModel by lazy {
        ViewModelProvider(this).get(AnimeViewModel::class.java)
    }
    private val TAG = AnimeTopFragment::class.java.simpleName

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
        val animeTopRecyclerView = binding.animeTopRecyclerView
        val shimmerLayout = binding.shimmerLayout

        animeViewModel.getAnimeListTopApi()
        animeViewModel.animeListTop.observe(viewLifecycleOwner, { animeTop ->
            when(animeTop) {
                is ScreenStateHelper.Loading -> {
                    shimmerLayout.startShimmer()
                }
                is ScreenStateHelper.Success -> {
                    val animeTopList = animeTop.data
                    animeTopRecyclerView.layoutManager =
                        GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                    animeHorizontalAdapter = AnimeHorizontalAdapter()
//                    animeAdapter.submitList(animeTopList)
                    animeHorizontalAdapter.setHasStableIds(true)
                    animeTopRecyclerView.setHasFixedSize(true)
                    animeTopRecyclerView.setItemViewCacheSize(6)
                    animeTopRecyclerView.isDrawingCacheEnabled = true
                    animeTopRecyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
                    animeTopRecyclerView.adapter = animeHorizontalAdapter
                    shimmerLayout.stopShimmer()
                    shimmerLayout.visibility = View.GONE
                    animeTopRecyclerView.visibility = View.VISIBLE
                    Log.i(TAG, "Success Anime Top List")
                }
                is ScreenStateHelper.Error -> {
                    Log.e(TAG, "Error Anime Top List in Anime Top Fragment With Code: ${animeTop.message}")
                }
                else -> {

                }
            }
        })
    }
}