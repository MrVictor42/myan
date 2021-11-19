package com.victor.myan.fragments.tablayouts.lists.top

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.adapter.MangaAdapter
import com.victor.myan.databinding.FragmentMangaTopBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.viewmodel.MangaViewModel

class MangaTopFragment : Fragment() {

    private lateinit var binding : FragmentMangaTopBinding
    private lateinit var mangaAdapter: MangaAdapter
    private val mangaViewModel by lazy {
        ViewModelProvider(this)[MangaViewModel::class.java]
    }

    companion object {
        fun newInstance(): MangaTopFragment {
            val mangaTopFragment = MangaTopFragment()
            val args = Bundle()
            mangaTopFragment.arguments = args
            return mangaTopFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMangaTopBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mangaTopRecyclerView = binding.recyclerView
        val shimmerLayout = binding.shimmerLayout

        mangaViewModel.getMangaListTopApi()
        mangaViewModel.mangaTopList.observe(viewLifecycleOwner, { mangaTop ->
            when(mangaTop) {
                is ScreenStateHelper.Loading -> {

                }
                is ScreenStateHelper.Success -> {
                    if(mangaTop.data != null) {
                        val mangaTopList = mangaTop.data
                        mangaTopRecyclerView.layoutManager =
                            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                        mangaAdapter = MangaAdapter()
                        mangaAdapter.setData(mangaTopList)
                        mangaTopRecyclerView.adapter = mangaAdapter
                        shimmerLayout.visibility = View.GONE
                        mangaTopRecyclerView.visibility = View.VISIBLE
                    }
                }
                is ScreenStateHelper.Error -> {

                }
                else -> {

                }
            }
        })
    }
}