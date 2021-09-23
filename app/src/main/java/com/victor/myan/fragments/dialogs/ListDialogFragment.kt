package com.victor.myan.fragments.dialogs

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.victor.myan.adapter.PersonalListAdapter
import com.victor.myan.databinding.FragmentListDialogBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.PersonalList
import com.victor.myan.model.User
import com.victor.myan.viewmodel.PersonalListViewModel
import com.victor.myan.viewmodel.UserViewModel

class ListDialogFragment : DialogFragment() {

    private lateinit var binding : FragmentListDialogBinding
    private lateinit var personalListAdapter : PersonalListAdapter
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
        personalListViewModel.getPersonalList()
        personalListViewModel.personalList.observe(viewLifecycleOwner, { personalList ->
            processPersonalListResponse(personalList)
        })
        userViewModel.getCurrentUser()
        userViewModel.currentUser.observe(viewLifecycleOwner, { user ->
            processCurrentUserResponse(user)
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

//        when (personalList) {
//            is ScreenStateHelper.Loading -> {
//                Log.i(TAG, "Loading personal list")
//            }
//            is ScreenStateHelper.Success -> {
//                if (personalList.data != null) {
//                    personalListRecyclerview.layoutManager =
//                        LinearLayoutManager(context, RecyclerView.VERTICAL, false)
//                    personalListAdapter = PersonalListAdapter()
//                    personalListAdapter.submitList(personalList.data)
//                    personalListRecyclerview.adapter = personalListAdapter
//                    personalListRecyclerview.visibility = View.VISIBLE
//                }
//            }
//            is ScreenStateHelper.Empty -> {
//                Log.i(TAG, personalList.message.toString())
//            }
//            else -> {
//
//            }
//        }
    }
}