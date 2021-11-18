package com.victor.myan.fragments.dialogs

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.victor.myan.R
import com.victor.myan.adapter.PersonalListAddAdapter
import com.victor.myan.databinding.FragmentListDialogBinding
import com.victor.myan.fragments.tablayouts.lists.personalList.CreateListFragment
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Anime
import com.victor.myan.model.Manga
import com.victor.myan.viewmodel.PersonalListViewModel
import com.victor.myan.viewmodel.UserViewModel

class ListDialogFragment(val anime: Anime?, val manga: Manga?) : DialogFragment() {

    private lateinit var binding : FragmentListDialogBinding
    private lateinit var personalListAddAdapter: PersonalListAddAdapter
    private val TAG = ListDialogFragment::class.java.simpleName
    private val personalListViewModel by lazy {
        ViewModelProvider(this)[PersonalListViewModel::class.java]
    }
    private val userViewModel by lazy {
        ViewModelProvider(this)[UserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog!!.window
        binding = FragmentListDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val linearUserName = binding.linearUserName
        val linearEmptyList = binding.emptyList
        val userName = binding.userName
        val personalListRecyclerview = binding.personalListRecyclerview
        val shimmerLayout = binding.shimmerLayout

        personalListRecyclerview.visibility = View.GONE
        linearEmptyList.visibility = View.GONE
        linearUserName.visibility = View.GONE

        userViewModel.getCurrentUser()
        userViewModel.currentUser.observe(viewLifecycleOwner, { user ->
            when(user) {
                is ScreenStateHelper.Loading -> {
                    Log.i(TAG, "Loading user...")
                }
                is ScreenStateHelper.Success -> {
                    if(user.data != null) {
                        userName.text = "Lists of ${ user.data.name }"
                        linearUserName.visibility = View.VISIBLE
                        Log.i(TAG, "User loaded!")
                    }
                }
                is ScreenStateHelper.Empty -> {
                    Log.i(TAG, user.message.toString())
                }
                else -> {

                }
            }
        })

        personalListViewModel.listRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.childrenCount > 0) {
                    personalListViewModel.getPersonalList()
                    personalListViewModel.personalList.observe(this@ListDialogFragment, { personalList ->
                        when (personalList) {
                            is ScreenStateHelper.Loading -> {
                                Log.i(TAG, "Loading personal list")
                                shimmerLayout.startShimmer()
                            }
                            is ScreenStateHelper.Success -> {
                                if (personalList.data != null) {
                                    personalListRecyclerview.layoutManager =
                                        LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                                    personalListAddAdapter = PersonalListAddAdapter(this@ListDialogFragment)
                                    personalListAddAdapter.submitList(personalList.data)

                                    if(anime != null) {
                                        personalListAddAdapter.setData(null, anime)
                                    } else if(manga != null) {
                                        personalListAddAdapter.setData(manga, null)
                                    }

                                    personalListRecyclerview.adapter = personalListAddAdapter
                                    shimmerLayout.stopShimmer()
                                    shimmerLayout.visibility = View.GONE
                                    personalListRecyclerview.visibility = View.VISIBLE
                                }
                            }
                            is ScreenStateHelper.Empty -> {
                                Log.i(TAG, personalList.message.toString())
                            }
                            else -> {

                            }
                        }
                    })
                } else {
                    linearEmptyList.visibility = View.VISIBLE
                    val btnAddList = binding.btnAddList
                    btnAddList.setOnClickListener {
                        val createListFragment = CreateListFragment()
                        (context as FragmentActivity)
                            .supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_layout, createListFragment)
                            .addToBackStack(null)
                            .commit()
                        dismiss()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Error Firebase")
            }
        })
    }
}