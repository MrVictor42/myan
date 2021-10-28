package com.victor.myan.fragments.tablayouts.listsDetail.day

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.victor.myan.R
import com.victor.myan.databinding.FragmentDayBinding
import com.victor.myan.fragments.tablayouts.listsDetail.top.AnimeTopFragment

class DayFragment : Fragment() {

    private lateinit var binding : FragmentDayBinding

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
        Toast.makeText(context, day, Toast.LENGTH_SHORT).show()
    }
}