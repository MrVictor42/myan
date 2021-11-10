package com.victor.myan.fragments.tablayouts.listsDetail.day

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.adapter.AnimeHorizontalAdapter
import com.victor.myan.databinding.FragmentDayBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.viewmodel.AnimeViewModel

class DayFragment : Fragment() {

    private lateinit var binding : FragmentDayBinding
    private lateinit var animeHorizontalAdapter: AnimeHorizontalAdapter
    private val TAG = DayFragment::class.java.simpleName
    private val animeViewModel by lazy {
        ViewModelProvider(this)[AnimeViewModel::class.java]
    }
    
    companion object {
        fun newInstance(day : String): DayFragment {
            val dayFragment = DayFragment()
            val args = Bundle()
            args.putString("day", day)
            dayFragment.arguments = args
            return dayFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDayBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val day = arguments?.getString("day")
        val dayRecyclerView = binding.dayRecyclerView
        val shimmerLayout = binding.shimmerLayout

        animeViewModel.getAnimeListTodayApi(day!!)
        animeViewModel.animeListToday.observe(viewLifecycleOwner, { animeToday ->
            when(animeToday) {
                is ScreenStateHelper.Loading -> {
                    shimmerLayout.startShimmer()
                    Log.i(TAG, "Loading Day List Anime")
                }
                is ScreenStateHelper.Success -> {
                    val animeTodayList = animeToday.data
                    dayRecyclerView.layoutManager =
                        GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                    animeHorizontalAdapter = AnimeHorizontalAdapter()
//                    animeAdapter.submitList(animeTodayList)
                    animeHorizontalAdapter.setHasStableIds(true)
                    dayRecyclerView.setHasFixedSize(true)
                    dayRecyclerView.setItemViewCacheSize(6)
                    dayRecyclerView.isDrawingCacheEnabled = true
                    dayRecyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
                    dayRecyclerView.adapter = animeHorizontalAdapter
                    shimmerLayout.stopShimmer()
                    shimmerLayout.visibility = View.GONE
                    dayRecyclerView.visibility = View.VISIBLE
                    Log.i(TAG, "Success Anime Top List")
                }
                is ScreenStateHelper.Error -> {
                    Log.e(TAG, "Error Day Anime List in Day Fragment With Code: ${animeToday.message}")
                }
                else -> {

                }    
                
            }
        })
    }
}