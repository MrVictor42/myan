package com.victor.myan.screens.characterDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.adapter.ActorAdapter
import com.victor.myan.databinding.FragmentVoiceCharacterBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Actor
import com.victor.myan.viewmodel.CharacterVoiceViewModel

class VoiceCharacterFragment : Fragment() {

    private lateinit var binding : FragmentVoiceCharacterBinding
    private lateinit var actorAdapter: ActorAdapter

    companion object {
        fun newInstance(mal_id : String): VoiceCharacterFragment {
            val voiceCharacterFragment = VoiceCharacterFragment()
            val args = Bundle()
            args.putString("mal_id", mal_id)
            voiceCharacterFragment.arguments = args
            return voiceCharacterFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVoiceCharacterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getString("mal_id").toString()
        val viewModel : CharacterVoiceViewModel by viewModels { CharacterVoiceViewModel.CharacterVoiceFactory(malID) }

        viewModel.characterVoiceLiveData.observe(this, { state ->
            processCharacterVoiceResponse(state)
        })
    }

    private fun processCharacterVoiceResponse(state: ScreenStateHelper<List<Actor>>?) {

        val characterVoiceRecyclerView = binding.recyclerViewCharacterVoice
        val emptyTextView = binding.emptyListTextView

        when(state) {
            is ScreenStateHelper.Loading -> {

            }
            is ScreenStateHelper.Success -> {
                if(state.data != null) {
                    val characterVoice = state.data
                    characterVoiceRecyclerView.setHasFixedSize(true)
                    characterVoiceRecyclerView.setItemViewCacheSize(10)
                    actorAdapter = ActorAdapter()
                    actorAdapter.submitList(characterVoice)
                    characterVoiceRecyclerView.layoutManager = GridLayoutManager(context, 2 , GridLayout.VERTICAL, false)
                    characterVoiceRecyclerView.adapter = actorAdapter
                    characterVoiceRecyclerView.visibility = View.VISIBLE
                }
            }
            is ScreenStateHelper.Empty -> {
                emptyTextView.text = state.message
                emptyTextView.visibility = View.VISIBLE
            }
            is ScreenStateHelper.Error -> {

            }
        }
    }
}