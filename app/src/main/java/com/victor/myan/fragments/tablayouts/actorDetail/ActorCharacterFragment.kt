package com.victor.myan.fragments.tablayouts.actorDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.adapter.CharactersAdapter
import com.victor.myan.databinding.FragmentActorCharacterBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Character
import com.victor.myan.viewmodel.ActorViewModel

class ActorCharacterFragment : Fragment() {

    private lateinit var binding : FragmentActorCharacterBinding
    private lateinit var characterAdapter : CharactersAdapter
    private val actorViewModel by lazy {
        ViewModelProvider(this).get(ActorViewModel::class.java)
    }
    private val TAG = ActorCharacterFragment::class.java.simpleName

    companion object {
        fun newInstance(mal_id : Int): ActorCharacterFragment {
            val actorCharacterFragment = ActorCharacterFragment()
            val args = Bundle()
            args.putInt("mal_id", mal_id)
            actorCharacterFragment.arguments = args
            return actorCharacterFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentActorCharacterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getInt("mal_id")!!

        actorViewModel.getActorCharacterApi(malID)
        actorViewModel.actorCharacterList.observe(viewLifecycleOwner, { state ->
            processActorCharacterResponse(state)
        })
    }

    private fun processActorCharacterResponse(state: ScreenStateHelper<List<Character>?>?) {
        val recyclerView = binding.recyclerView.recyclerViewVertical
        val shimmerLayout = binding.shimmerLayout

        when(state) {
            is ScreenStateHelper.Loading -> {
                shimmerLayout.startShimmer()
                Log.i(TAG, "Loading Actor Character List")
            }
            is ScreenStateHelper.Success -> {
                if(state.data != null) {
                    val characterList = state.data
                    recyclerView.setHasFixedSize(true)
                    recyclerView.setItemViewCacheSize(10)
                    characterAdapter = CharactersAdapter()
//                    characterAdapter.submitList(characterList)
                    recyclerView.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                    recyclerView.adapter = characterAdapter
                    shimmerLayout.stopShimmer()
                    shimmerLayout.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    Log.i(TAG, "Success Actor Character List")
                }
            }
            is ScreenStateHelper.Error -> {
                Log.e(TAG, "Error Actor Character List in Actor Character Fragment With Code: ${state.message}")
            }
            else -> {
                // Nothing to do
            }
        }
    }
}