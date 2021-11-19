package com.victor.myan.fragments.tablayouts.lists.day

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.adapter.AnimeAdapter
import com.victor.myan.databinding.FragmentDayBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.viewmodel.AnimeViewModel

class DayFragment : Fragment() {

    private lateinit var binding : FragmentDayBinding
    private lateinit var animeAdapter: AnimeAdapter
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
        val dayRecyclerView = binding.recyclerView
        val shimmerLayout = binding.shimmerLayout

        animeViewModel.getAnimeListTodayApi(day!!)
        animeViewModel.animeListToday.observe(viewLifecycleOwner, { animeToday ->
            when(animeToday) {
                is ScreenStateHelper.Loading -> {

                }
                is ScreenStateHelper.Success -> {
                    if(animeToday.data != null) {
                        val animeTopList = animeToday.data
                        dayRecyclerView.layoutManager =
                            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                        animeAdapter = AnimeAdapter()
                        animeAdapter.setData(animeTopList)
                        dayRecyclerView.adapter = animeAdapter
                        shimmerLayout.visibility = View.GONE
                        dayRecyclerView.visibility = View.VISIBLE
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