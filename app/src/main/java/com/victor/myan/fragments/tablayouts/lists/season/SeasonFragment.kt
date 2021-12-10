package com.victor.myan.fragments.tablayouts.lists.season

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.adapter.AnimeAdapter
import com.victor.myan.databinding.FragmentSeasonBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.viewmodel.AnimeViewModel
import com.whiteelephant.monthpicker.MonthPickerDialog
import java.util.Calendar

class SeasonFragment : Fragment() {

    private lateinit var binding : FragmentSeasonBinding
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
        getSelectedSeason(season, currentYear, savedInstanceState, view)

        btnYear.setOnClickListener {
            val builder : MonthPickerDialog.Builder = MonthPickerDialog.Builder(context, { _, selectedYear ->
                    btnYear.text = selectedYear.toString()
                    currentYear = selectedYear
                    getSelectedSeason(season, currentYear, savedInstanceState, view)
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

    private fun getSelectedSeason(
        season: String,
        currentYear: Int,
        savedInstanceState: Bundle?,
        view: View
    ) {
        val seasonRecyclerView = binding.recyclerView
        val shimmerLayout = binding.shimmerLayout
        val errorOptions = binding.errorOptions.errorOptions
        val btnRefresh = binding.errorOptions.btnRefresh

        animeViewModel.getAnimeListSeasonApi(currentYear, season)
        animeViewModel.animeListSeason.observe(viewLifecycleOwner, { animeSeason ->
            when(animeSeason) {
                is ScreenStateHelper.Loading -> {

                }
                is ScreenStateHelper.Success -> {
                    if(animeSeason.data != null) {
                        val animeSeasonList = animeSeason.data
                        seasonRecyclerView.layoutManager =
                            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                        animeAdapter = AnimeAdapter()
                        animeAdapter.setData(animeSeasonList)
                        seasonRecyclerView.adapter = animeAdapter
                        shimmerLayout.visibility = View.GONE
                        seasonRecyclerView.visibility = View.VISIBLE
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