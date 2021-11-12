package com.victor.myan.fragments.tablayouts.actor

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.adapter.AnimeHorizontalAdapter
import com.victor.myan.databinding.FragmentAnimeActorBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Anime
import com.victor.myan.viewmodel.ActorViewModel

class ActorAnimeFragment : Fragment() {

    private lateinit var binding : FragmentAnimeActorBinding
    private lateinit var animeHorizontalAdapter: AnimeHorizontalAdapter
    private val actorViewModel by lazy {
        ViewModelProvider(this).get(ActorViewModel::class.java)
    }
    private val TAG = ActorAnimeFragment::class.java.simpleName

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

        actorViewModel.getActorAnimeApi(malID)
        actorViewModel.actorAnimeList.observe(viewLifecycleOwner, { actorAnime ->

        })
    }

//    private fun processActorAnimeResponse(state: ScreenStateHelper<List<Anime>?>?) {
//        val actorAnimeRecyclerView = binding.recyclerView.recyclerViewVertical
//        val shimmerLayout = binding.shimmerLayout
//
//        when(state) {
//            is ScreenStateHelper.Loading -> {
//                shimmerLayout.startShimmer()
//                Log.i(TAG, "Loading ActorAnimeFragment")
//            }
//            is ScreenStateHelper.Success -> {
//                if(state.data != null) {
//                    val actorAnime = state.data
//                    actorAnimeRecyclerView.setHasFixedSize(true)
//                    actorAnimeRecyclerView.setItemViewCacheSize(8)
//                    animeHorizontalAdapter = AnimeHorizontalAdapter()
////                    animeAdapter.submitList(actorAnime)
//                    actorAnimeRecyclerView.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
//                    actorAnimeRecyclerView.adapter = animeHorizontalAdapter
//                    actorAnimeRecyclerView.visibility = View.VISIBLE
//
//                    shimmerLayout.stopShimmer()
//                    shimmerLayout.visibility = View.GONE
//                    actorAnimeRecyclerView.visibility = View.VISIBLE
//                    Log.i(TAG, "Success Actor Anime List")
//                }
//            }
//            is ScreenStateHelper.Error -> {
//                Log.e(TAG, "Error ActorAnime with code: ${state.message}")
//            }
//            else -> {
//
//            }
//        }
//    }
}