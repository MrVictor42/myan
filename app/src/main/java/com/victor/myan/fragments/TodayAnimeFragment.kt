package com.victor.myan.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.victor.myan.adapter.AnimeAdapter
import com.victor.myan.databinding.FragmentTodayAnimeBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Anime
import com.victor.myan.viewmodel.TodayAnimeViewModel

class TodayAnimeFragment : Fragment() {

    private lateinit var binding : FragmentTodayAnimeBinding
    private lateinit var animeAdapter: AnimeAdapter
    private val viewModel by lazy { ViewModelProvider(this).get(TodayAnimeViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodayAnimeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.animeToday.observe(viewLifecycleOwner, { state ->
            processAnimeListCarouselResponse(state)
        })
    }

    @SuppressLint("InflateParams")
    private fun processAnimeListCarouselResponse(state: ScreenStateHelper<List<Anime>?>?) {
        val todayAnimeText = binding.todayAnimeText
        val todayAnimeRecyclerView = binding.recyclerViewToday
        todayAnimeText.text = viewModel.currentDayFormatted

        when(state) {
            is ScreenStateHelper.Loading -> {
                // Aqui vai ficar o lance legal de carregamento de página que não foi feito ainda
            }
            is ScreenStateHelper.Success -> {
                if(state.data != null) {
                    val animeList = state.data
                    todayAnimeRecyclerView.layoutManager =
                        LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                    animeAdapter = AnimeAdapter()
                    animeAdapter.submitList(animeList)
                    todayAnimeRecyclerView.adapter = animeAdapter
                }
            }
            is ScreenStateHelper.Error -> {
                val view = binding.todayAnimeContainer
                Snackbar.make(view, "Connection with internet not found...", Snackbar.LENGTH_LONG).show()
            }
            else -> {

            }
        }
    }
}