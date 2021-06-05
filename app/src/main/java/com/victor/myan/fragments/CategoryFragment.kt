package com.victor.myan.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.victor.myan.adapter.CategoryAdapter
import com.victor.myan.databinding.FragmentCategoryBinding
import com.victor.myan.model.Category

class CategoryFragment : Fragment() {

    private lateinit var binding : FragmentCategoryBinding
    private lateinit var categoryAdapter : CategoryAdapter

    companion object {
        fun newInstance(): CategoryFragment {
            val catalogFragment = CategoryFragment()
            val args = Bundle()
            catalogFragment.arguments = args
            return catalogFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val categoryList = arrayListOf<Category>()
        val recyclerViewCatalog = binding.recyclerViewCatalog
        recyclerViewCatalog.layoutManager =
            LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)

        categoryAdapter = CategoryAdapter(categoryList)
        recyclerViewCatalog.adapter = categoryAdapter
        recyclerViewCatalog.layoutManager = GridLayoutManager(context, 2)

        categoryAdapter.categories.clear()

        FirebaseFirestore.getInstance().collection("categories")
            .addSnapshotListener {
                snapshot, exception -> exception?.let {
            return@addSnapshotListener
        }
            snapshot?.let {
                for(doc in snapshot) {
                    val category = Category()

                    category.type = doc.get("type").toString()
                    category.image = doc.get("image").toString()
                    categoryAdapter.categories.add(category)
                }
                categoryAdapter.notifyDataSetChanged()
            }
        }
    }
}
