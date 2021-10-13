package com.victor.myan.fragments.tablayouts.listsDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victor.myan.R
import com.victor.myan.adapter.PersonalListAdapter
import com.victor.myan.databinding.FragmentPersonalListBinding
import com.victor.myan.fragments.tablayouts.listsDetail.personalList.CreateListFragment
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.PersonalList
import com.victor.myan.viewmodel.PersonalListViewModel

class PersonalListFragment : Fragment() {

    private lateinit var binding : FragmentPersonalListBinding
    private lateinit var personalListAdapter : PersonalListAdapter
    private val TAG = PersonalListFragment::class.java.simpleName
    private val personalListViewModel by lazy {
        ViewModelProvider(this).get(PersonalListViewModel::class.java)
    }

    companion object {
        fun newInstance(): PersonalListFragment {
            val personalListFragment = PersonalListFragment()
            val args = Bundle()
            personalListFragment.arguments = args
            return personalListFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        personalListViewModel.getPersonalList()
        personalListViewModel.personalList.observe(viewLifecycleOwner, { personalList ->
            processPersonalListResponse(personalList)
        })
    }

    private fun processPersonalListResponse(personalList: ScreenStateHelper<List<PersonalList>?>?) {
        val btnRegisterList = binding.btnRegisterList
        val btnAddList = binding.btnAddList
        val createListNotEmpty = binding.createListNotEmpty
        val createListEmpty = binding.createListEmpty
        val personalListRecyclerview = binding.personalListRecyclerview
        val shimmerLayout = binding.shimmerLayout

        createListNotEmpty.visibility = View.GONE
        createListEmpty.visibility = View.GONE
        shimmerLayout.visibility = View.GONE

        btnRegisterList.setOnClickListener {
            val createListFragment = CreateListFragment()
            (context as FragmentActivity)
                .supportFragmentManager
                .beginTransaction()
                .remove(this)
                .replace(R.id.fragment_layout, createListFragment)
                .addToBackStack(null)
                .commit()
        }

        btnAddList.setOnClickListener {
            val createListFragment = CreateListFragment()
            (context as FragmentActivity)
                .supportFragmentManager
                .beginTransaction()
                .remove(this)
                .replace(R.id.fragment_layout, createListFragment)
                .addToBackStack(null)
                .commit()
        }

        when (personalList) {
            is ScreenStateHelper.Loading -> {
                Log.i(TAG, "Loading personal list")
                shimmerLayout.visibility = View.VISIBLE
                shimmerLayout.startShimmer()
            }
            is ScreenStateHelper.Success -> {
                if (personalList.data != null) {
                    createListNotEmpty.visibility = View.VISIBLE
                    createListEmpty.visibility = View.GONE

                    personalListRecyclerview.layoutManager =
                        LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                    personalListAdapter = PersonalListAdapter()
                    personalListAdapter.submitList(personalList.data)
                    personalListRecyclerview.adapter = personalListAdapter
                    personalListRecyclerview.visibility = View.VISIBLE
                    shimmerLayout.stopShimmer()
                    shimmerLayout.visibility = View.GONE
                } else {
                    createListNotEmpty.visibility = View.GONE
                    createListEmpty.visibility = View.VISIBLE
                }
            }
            is ScreenStateHelper.Empty -> {
                Log.i(TAG, personalList.message.toString())
                createListEmpty.visibility = View.VISIBLE
                createListNotEmpty.visibility = View.GONE
            }
            else -> {

            }
        }
    }
}