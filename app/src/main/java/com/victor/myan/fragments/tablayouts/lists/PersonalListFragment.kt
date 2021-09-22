package com.victor.myan.fragments.tablayouts.lists

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.victor.myan.R
import com.victor.myan.adapter.PersonalListAdapter
import com.victor.myan.databinding.FragmentPersonalListBinding
import com.victor.myan.fragments.tablayouts.lists.crud.CreateListFragment
import com.victor.myan.model.PersonalList

class PersonalListFragment : Fragment() {

    private lateinit var binding : FragmentPersonalListBinding
    private lateinit var personalListAdapter : PersonalListAdapter

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
        val btnRegisterList = binding.btnRegisterList
        val currentUser = FirebaseAuth.getInstance().currentUser!!.uid
        val listRef =
            FirebaseDatabase
                .getInstance()
                .getReference("list")
                .orderByChild("userID")
                .equalTo(currentUser)
        val btnAddList = binding.btnAddList
        val createListNotEmpty = binding.createListNotEmpty
        val createListEmpty = binding.createListEmpty
        val personalListRecyclerview = binding.personalListRecyclerview

        createListNotEmpty.visibility = View.GONE
        createListEmpty.visibility = View.GONE

        btnRegisterList.setOnClickListener {
            val createListFragment = CreateListFragment()
            (view.context as FragmentActivity)
                .supportFragmentManager
                .beginTransaction()
                .remove(this)
                .replace(R.id.fragment_layout, createListFragment)
                .addToBackStack(null)
                .commit()
        }

        btnAddList.setOnClickListener {
            val createListFragment = CreateListFragment()
            (view.context as FragmentActivity)
                .supportFragmentManager
                .beginTransaction()
                .remove(this)
                .replace(R.id.fragment_layout, createListFragment)
                .addToBackStack(null)
                .commit()
        }

        listRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    createListNotEmpty.visibility = View.VISIBLE
                    createListEmpty.visibility = View.GONE
                    val personalList : MutableList<PersonalList> = arrayListOf()

                    for(postSnapshot in snapshot.children) {
                        personalList.add(postSnapshot.getValue(PersonalList::class.java)!!)
                    }

                    personalListRecyclerview.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                    personalListAdapter = PersonalListAdapter()
                    personalListAdapter.submitList(personalList)
                    personalListRecyclerview.adapter = personalListAdapter
                    personalListRecyclerview.visibility = View.VISIBLE
                } else {
                    createListEmpty.visibility = View.VISIBLE
                    createListNotEmpty.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}