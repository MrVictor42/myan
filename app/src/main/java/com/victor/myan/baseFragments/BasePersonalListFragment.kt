package com.victor.myan.baseFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.victor.myan.R
import com.victor.myan.databinding.FragmentBasePersonalListBinding
import com.victor.myan.viewmodel.PersonalListViewModel
import com.victor.myan.viewpager.PersonalListViewPager

class BasePersonalListFragment(
    private val id: String, private val description: String,
    private val image: String, private val name: String
) : Fragment() {

    private lateinit var binding : FragmentBasePersonalListBinding
    private val personalListViewModel by lazy {
        ViewModelProvider(this)[PersonalListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBasePersonalListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val imageList = binding.imagePersonalList
        val nameList = binding.namePersonalList
        val btnDeleteList = binding.btnDeleteList
        val descriptionList = binding.descriptionPersonalList
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager2
        val sizePager = 2
        val adapter = PersonalListViewPager(parentFragmentManager, lifecycle, id, sizePager)

        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false
        TabLayoutMediator(tabLayout, viewPager, true, false){ tab, position ->
            when(position) {
                0 -> tab.text = "Anime"
                1 -> tab.text = "Manga"
            }
        }.attach()

        Glide.with(view.context).load(image).into(imageList)
        nameList.text = name
        descriptionList.text = description

        btnDeleteList.setOnClickListener {
            val alertBuilder = AlertDialog.Builder(requireContext())

            alertBuilder.setTitle("Delete List")
            alertBuilder.setMessage("Do you really want to delete this list ?")
            alertBuilder.setPositiveButton("Yes"){ _,_ ->
                val listRef = personalListViewModel.listRef.ref.child(id)
                listRef.removeValue().addOnSuccessListener {
                    Snackbar.make(
                        binding.basePersonalList,
                        "List deleted with success!!",
                        Snackbar.LENGTH_LONG
                    ).show()
                    val baseListsFragment = BaseListsFragment()
                    (view.context as FragmentActivity)
                        .supportFragmentManager
                        .beginTransaction()
                        .remove(this)
                        .replace(R.id.fragment_layout, baseListsFragment)
                        .addToBackStack(null)
                        .commit()
                }.addOnFailureListener {
                    Snackbar.make(
                        binding.basePersonalList,
                        "Something happened wrong... Try again later",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
            alertBuilder.setNegativeButton("No"){_,_ ->

            }

            alertBuilder.setNeutralButton("Cancel"){_,_ ->

            }
            alertBuilder.show()
        }
    }
}