package com.victor.myan.fragments.tablayouts.listsDetail.season

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.myan.databinding.FragmentSeasonBinding
import com.whiteelephant.monthpicker.MonthPickerDialog
import java.util.Calendar

class SeasonFragment : Fragment() {

    private lateinit var binding : FragmentSeasonBinding

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
        val season = arguments?.getString("season")
        val btnYear = binding.btnYear
        var currentYear = Calendar.getInstance().get(Calendar.YEAR)
        btnYear.text = currentYear.toString()

        btnYear.setOnClickListener {
            val builder : MonthPickerDialog.Builder = MonthPickerDialog.Builder(context, { _, selectedYear ->
                    btnYear.text = selectedYear.toString()
                    currentYear = selectedYear
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
}