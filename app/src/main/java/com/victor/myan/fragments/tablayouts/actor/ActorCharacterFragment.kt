package com.victor.myan.fragments.tablayouts.actor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.adapter.CharactersAdapter
import com.victor.myan.databinding.FragmentActorCharacterBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.viewmodel.ActorViewModel

class ActorCharacterFragment : Fragment() {

    private lateinit var binding : FragmentActorCharacterBinding
    private lateinit var characterAdapter : CharactersAdapter
    private val actorViewModel by lazy {
        ViewModelProvider(this)[ActorViewModel::class.java]
    }

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
        val shimmerLayout = binding.shimmerLayout
        val actorCharacterRecyclerView = binding.recyclerView

        actorViewModel.getActorCharacterApi(malID)
        actorViewModel.actorCharacterList.observe(viewLifecycleOwner, { actorCharacter ->
            when(actorCharacter) {
                is ScreenStateHelper.Loading -> {
                    shimmerLayout.startShimmer()
                }
                is ScreenStateHelper.Success -> {
                    if (actorCharacter.data != null) {
                        val characterList = actorCharacter.data
                        actorCharacterRecyclerView.layoutManager =
                            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                        characterAdapter = CharactersAdapter()
                        characterAdapter.setData(characterList)
                        actorCharacterRecyclerView.adapter = characterAdapter
                        shimmerLayout.stopShimmer()
                        shimmerLayout.visibility = View.GONE
                        actorCharacterRecyclerView.visibility = View.VISIBLE
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