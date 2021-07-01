package com.victor.myan.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.victor.myan.adapter.CategoriesAdapter
import com.victor.myan.databinding.FragmentCategoriesListBinding
import com.victor.myan.enums.VariablesEnum
import com.victor.myan.model.Category

class CategoriesListFragment : Fragment() {

    private lateinit var binding : FragmentCategoriesListBinding
    private lateinit var categoriesAdapter : CategoriesAdapter

    companion object {
        fun newInstance(): CategoriesListFragment {
            val catalogFragment = CategoriesListFragment()
            val args = Bundle()
            catalogFragment.arguments = args
            return catalogFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val categoryList = arrayListOf<Category>()
        val recyclerViewCatalog = binding.recyclerViewCatalog
        recyclerViewCatalog.layoutManager =
            LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)

        categoriesAdapter = CategoriesAdapter(categoryList)
        recyclerViewCatalog.adapter = categoriesAdapter
        recyclerViewCatalog.layoutManager = GridLayoutManager(context, 2)

        categoriesAdapter.categories.clear()

        FirebaseFirestore.getInstance().collection(VariablesEnum.Categories.variable)
            .addSnapshotListener {
                snapshot, exception -> exception?.let {
            return@addSnapshotListener
        }
            snapshot?.let {
                for(doc in snapshot) {
                    val category = Category()

                    category.type = doc.get(VariablesEnum.Type.variable).toString()
                    category.image = doc.get(VariablesEnum.ImageCategory.variable).toString()
                    category.genre = Integer.parseInt(doc.get(VariablesEnum.Genre.variable).toString())
                    categoriesAdapter.categories.add(category)
                }
                categoriesAdapter.notifyDataSetChanged()
            }
        }
    }
}