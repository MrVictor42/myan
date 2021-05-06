package com.victor.myan.fragmentsTab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victor.myan.R
import com.victor.myan.adapter.RecyclerViewAdapter
import com.victor.myan.fragments.HomeFragment
import com.victor.myan.model.RecyclerList
import com.victor.myan.viewmodel.TodayAnimeViewlModel

class TodayAnimeFragment : Fragment() {

    private lateinit var recyclerAdapter : RecyclerViewAdapter

    companion object {
        fun newInstance(): TodayAnimeFragment {
            val todayAnimeFragment = TodayAnimeFragment()
            val args = Bundle()
            todayAnimeFragment.arguments = args
            return todayAnimeFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_today_anime, container, false)

        initViewModel(view)
        initViewModel()
        return view
    }

    private fun initViewModel(view: View?) {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerTodayAnime)
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        val decortion = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        recyclerView?.addItemDecoration(decortion)
        recyclerAdapter = RecyclerViewAdapter()
        recyclerView?.adapter = recyclerAdapter
    }

    private fun initViewModel() {
        val viewModel = ViewModelProvider(this).get(TodayAnimeViewlModel::class.java)
        viewModel.getRecyclerListObverser().observe(this, Observer<RecyclerList> {
            if(it != null) {
                recyclerAdapter.setUpdateData(it.items)
            } else {
                Toast.makeText(activity, "Error in getting data", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeApiCall()
    }
}