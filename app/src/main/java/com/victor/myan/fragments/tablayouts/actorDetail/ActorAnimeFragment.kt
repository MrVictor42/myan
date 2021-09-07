package com.victor.myan.fragments.tablayouts.actorDetail

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
import com.victor.myan.model.Anime
import com.victor.myan.viewmodel.ActorViewModel

class ActorAnimeFragment : Fragment() {

    private lateinit var binding : FragmentAnimeActorBinding
    private lateinit var animeAdapter: AnimeAdapter
    private val actorViewModel by lazy {
        ViewModelProvider(this).get(ActorViewModel::class.java)
    }

    companion object {
        fun newInstance(mal_id : String): ActorAnimeFragment {
            val animePersonFragment = ActorAnimeFragment()
            val args = Bundle()
            args.putString("mal_id", mal_id)
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
        val malID = arguments?.getString("mal_id").toString()

        actorViewModel.getActorAnimeApi(malID)
        actorViewModel.actorAnimeList.observe(viewLifecycleOwner, { state ->
            processActorAnimeResponse(state)
        })
    }

    private fun processActorAnimeResponse(state: ScreenStateHelper<List<Anime>?>?) {
        val malID = arguments?.getString("mal_id").toString()
        val actorAnimeRecyclerView = binding.recyclerView.recyclerViewVertical

        when(state) {
            is ScreenStateHelper.Loading -> {

            }
            is ScreenStateHelper.Success -> {
                if(state.data != null) {
                    val actorAnime = state.data
                    actorAnimeRecyclerView.setHasFixedSize(true)
                    actorAnimeRecyclerView.setItemViewCacheSize(10)
                    animeAdapter = AnimeAdapter()
                    animeAdapter.submitList(actorAnime)
                    actorAnimeRecyclerView.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                    actorAnimeRecyclerView.adapter = animeAdapter
                    actorAnimeRecyclerView.visibility = View.VISIBLE
                }
            }
            is ScreenStateHelper.Error -> {
                actorViewModel.getActorAnimeApi(malID)
            }
            else -> {

            }
        }
    }
}