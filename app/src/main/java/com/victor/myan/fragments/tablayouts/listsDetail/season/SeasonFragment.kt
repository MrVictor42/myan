package com.victor.myan.fragments.tablayouts.listsDetail.season

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victor.myan.adapter.AnimeAdapter
import com.victor.myan.databinding.FragmentSeasonBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.viewmodel.AnimeViewModel
import com.whiteelephant.monthpicker.MonthPickerDialog
import java.util.Calendar

class SeasonFragment : Fragment() {

    private lateinit var binding : FragmentSeasonBinding
    private val TAG = SeasonFragment::class.java.simpleName
    private lateinit var animeAdapter: AnimeAdapter
    private val animeViewModel by lazy {
        ViewModelProvider(this)[AnimeViewModel::class.java]
    }

    companion object {
        fun newInstance(season : String): SeasonFragment {
            val seasonFragment = SeasonFragment()
            val args = Bundle()
            args.putString("season", season)
            seasonFragment.arguments = args
            return seasonFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSeasonBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val season = arguments?.getString("season")!!
        val btnYear = binding.btnYear
        var currentYear = Calendar.getInstance().get(Calendar.YEAR)

        btnYear.text = currentYear.toString()
        getSelectedSeason(season, currentYear)

        btnYear.setOnClickListener {
            val builder : MonthPickerDialog.Builder = MonthPickerDialog.Builder(context, { _, selectedYear ->
                    btnYear.text = selectedYear.toString()
                    currentYear = selectedYear
                    getSelectedSeason(season, currentYear)
                }, Calendar.getInstance().get(Calendar.YEAR), 0
            )
            builder
                .setMinYear(1957)
                .setMaxYear(Calendar.getInstance().get(Calendar.YEAR))
                .setTitle("Select Year")
                .setActivatedYear(currentYear)
                .showYearOnly()
                .build().show()
        }
    }

    private fun getSelectedSeason(season: String, currentYear : Int) {
        val seasonRecyclerView = binding.seasonRecyclerView
        val shimmerLayout = binding.shimmerLayout

        animeViewModel.getAnimeListSeasonApi(currentYear, season)
        animeViewModel.animeListSeason.observe(viewLifecycleOwner, { state ->
            when(state) {
                is ScreenStateHelper.Loading -> {
                    shimmerLayout.startShimmer()
                    Log.i(TAG, "Loading Anime List Season")
                }
                is ScreenStateHelper.Success -> {
                    if(state.data != null) {
                        val animeList = state.data
                        seasonRecyclerView.layoutManager =
                            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                        animeAdapter = AnimeAdapter()
                        animeAdapter.submitList(animeList)
                        animeAdapter.setHasStableIds(true)
                        seasonRecyclerView.setHasFixedSize(true)
                        seasonRecyclerView.setItemViewCacheSize(6)
                        seasonRecyclerView.isDrawingCacheEnabled = true
                        seasonRecyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
                        seasonRecyclerView.adapter = animeAdapter
                        shimmerLayout.stopShimmer()
                        shimmerLayout.visibility = View.GONE
                        seasonRecyclerView.visibility = View.VISIBLE
                        Log.i(TAG, "Success Anime List Season")
                    }
                }
                is ScreenStateHelper.Error -> {
                    Log.e(TAG, "Error Anime List Season in Home Fragment With Code: ${state.message}")
                }
                else -> {

                }
            }
        })
    }
}