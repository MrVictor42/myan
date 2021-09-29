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

class ListDialogFragment(val anime: Anime, val manga: Manga?) : DialogFragment() {

    private lateinit var binding : FragmentListDialogBinding
    private lateinit var personalListAddRemoveAdapter: PersonalListAddRemoveAdapter
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
        val btnAddList = binding.btnAddList
        val emptyList = binding.emptyListTextView

        userViewModel.getCurrentUser()
        userViewModel.currentUser.observe(viewLifecycleOwner, { user ->
            processCurrentUserResponse(user)
        })

        when(personalListViewModel.existsList()) {
            true -> {
                personalListViewModel.getPersonalList()
                personalListViewModel.personalList.observe(viewLifecycleOwner, { personalList ->
                    processPersonalListResponse(personalList)
                })
            }
            false -> {
                btnAddList.visibility = View.VISIBLE
                emptyList.visibility = View.VISIBLE

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

        when (personalList) {
            is ScreenStateHelper.Loading -> {
                Log.i(TAG, "Loading personal list")
            }
            is ScreenStateHelper.Success -> {
                if (personalList.data != null) {
                    personalListRecyclerview.layoutManager =
                        LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                    personalListAddRemoveAdapter = PersonalListAddRemoveAdapter()
                    personalListAddRemoveAdapter.submitList(personalList.data)
                    personalListAddRemoveAdapter.addAnime(anime)
                    personalListRecyclerview.adapter = personalListAddRemoveAdapter
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