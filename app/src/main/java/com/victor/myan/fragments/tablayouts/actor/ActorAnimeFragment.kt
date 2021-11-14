package com.victor.myan.fragments.tablayouts.actor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.adapter.AnimeAdapter
import com.victor.myan.databinding.FragmentAnimeActorBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.viewmodel.ActorViewModel

class ActorAnimeFragment : Fragment() {

    private lateinit var binding : FragmentAnimeActorBinding
    private lateinit var animeAdapter: AnimeAdapter
    private val actorViewModel by lazy {
        ViewModelProvider(this)[ActorViewModel::class.java]
    }

    companion object {
        fun newInstance(mal_id : Int): ActorAnimeFragment {
            val animePersonFragment = ActorAnimeFragment()
            val args = Bundle()
            args.putInt("mal_id", mal_id)
            animePersonFragment.arguments = args
            return animePersonFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimeActorBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getInt("mal_id")!!
        val actorAnimeRecyclerView = binding.recyclerView
        val shimmerLayout = binding.shimmerLayout

        actorViewModel.getActorAnimeApi(malID)
        actorViewModel.actorAnimeList.observe(viewLifecycleOwner, { actorAnime ->
            when(actorAnime) {
                is ScreenStateHelper.Loading -> {
                    shimmerLayout.startShimmer()
                }
                is ScreenStateHelper.Success -> {
                    if(actorAnime.data != null) {
                        val actorAnimeList = actorAnime.data
                        actorAnimeRecyclerView.layoutManager =
                            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                        animeAdapter = AnimeAdapter()
                        animeAdapter.setData(actorAnimeList)
                        actorAnimeRecyclerView.adapter = animeAdapter
                        shimmerLayout.stopShimmer()
                        shimmerLayout.visibility = View.GONE
                        actorAnimeRecyclerView.visibility = View.VISIBLE
                    }
                }
                else -> {

                }
            }
        })
    }
}