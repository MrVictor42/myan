package com.victor.myan.fragments.dialogs

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.victor.myan.R
import com.victor.myan.adapter.PersonalListAddRemoveAdapter
import com.victor.myan.databinding.FragmentListDialogBinding
import com.victor.myan.fragments.tablayouts.listsDetail.personalList.CreateListFragment
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Anime
import com.victor.myan.model.Manga
import com.victor.myan.model.PersonalList
import com.victor.myan.model.User
import com.victor.myan.viewmodel.PersonalListViewModel
import com.victor.myan.viewmodel.UserViewModel

class ListDialogFragment(val anime: Anime?, val manga: Manga?) : DialogFragment() {

    private lateinit var binding : FragmentListDialogBinding
    private lateinit var personalListAddRemoveAdapter: PersonalListAddRemoveAdapter
    private lateinit var linearEmptyList : LinearLayoutCompat
    private lateinit var linearUserName : LinearLayoutCompat
    private val TAG = ListDialogFragment::class.java.simpleName
    private val personalListViewModel by lazy {
        ViewModelProvider(this).get(PersonalListViewModel::class.java)
    }
    private val userViewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog!!.window
        binding = FragmentListDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        linearUserName = binding.linearUserName
        linearUserName.visibility = View.GONE
        linearEmptyList = binding.emptyList
        linearEmptyList.visibility = View.GONE

        userViewModel.getCurrentUser()
        userViewModel.currentUser.observe(viewLifecycleOwner, { user ->
            processCurrentUserResponse(user)
        })

        personalListViewModel.listRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.childrenCount > 0) {
                    personalListViewModel.getPersonalList()
                    personalListViewModel.personalList.observe(this@ListDialogFragment, { personalList ->
                        processPersonalListResponse(personalList)
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

    @SuppressLint("SetTextI18n")
    private fun processCurrentUserResponse(user: ScreenStateHelper<User>?) {
        val userName = binding.userName

        when(user) {
            is ScreenStateHelper.Loading -> {
                Log.i(TAG, "Loading user...")
            }
            is ScreenStateHelper.Success -> {
                if(user.data != null) {
                    userName.text = "Lists of ${user.data.name}"
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
    }

    private fun processPersonalListResponse(personalList: ScreenStateHelper<List<PersonalList>?>?) {
        val personalListRecyclerview = binding.personalListRecyclerview
        val shimmerLayout = binding.shimmerLayout

        personalListRecyclerview.visibility = View.GONE
        when (personalList) {
            is ScreenStateHelper.Loading -> {
                Log.i(TAG, "Loading personal list")
                shimmerLayout.startShimmer()
            }
            is ScreenStateHelper.Success -> {
                if (personalList.data != null) {
                    personalListRecyclerview.layoutManager =
                        LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                    personalListAddRemoveAdapter = PersonalListAddRemoveAdapter(this)
                    personalListAddRemoveAdapter.submitList(personalList.data)

                    if(anime != null) {
                        personalListAddRemoveAdapter.addAnime(anime)
                    } else if(manga != null) {
                        personalListAddRemoveAdapter.addManga(manga)
                    }

                    personalListRecyclerview.adapter = personalListAddRemoveAdapter
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
    }
}