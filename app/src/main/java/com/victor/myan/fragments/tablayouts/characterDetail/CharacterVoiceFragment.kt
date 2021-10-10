package com.victor.myan.fragments.tablayouts.characterDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.adapter.ActorAdapter
import com.victor.myan.databinding.FragmentCharacterVoiceBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Actor
import com.victor.myan.viewmodel.CharacterViewModel

class CharacterVoiceFragment : Fragment() {

    private lateinit var binding : FragmentCharacterVoiceBinding
    private lateinit var actorAdapter: ActorAdapter
    private val characterViewModel by lazy {
        ViewModelProvider(this).get(CharacterViewModel::class.java)
    }
    private val TAG = CharacterVoiceFragment::class.java.simpleName

    companion object {
        fun newInstance(mal_id : Int): CharacterVoiceFragment {
            val voiceCharacterFragment = CharacterVoiceFragment()
            val args = Bundle()
            args.putInt("mal_id", mal_id)
            voiceCharacterFragment.arguments = args
            return voiceCharacterFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterVoiceBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getInt("mal_id")!!

        characterViewModel.getCharacterVoiceApi(malID)
        characterViewModel.characterVoiceList.observe(viewLifecycleOwner, { state ->
            processCharacterVoiceResponse(state)
        })
    }

    private fun processCharacterVoiceResponse(state: ScreenStateHelper<List<Actor>?>) {
        val characterVoiceRecyclerView = binding.recyclerView.recyclerViewVertical
        val emptyText = binding.emptyListTextView
        val shimmerLayout = binding.shimmerLayout

        when(state) {
            is ScreenStateHelper.Loading -> {
                shimmerLayout.startShimmer()
                Log.i(TAG, "CharacterVoiceFragment Loading...")
            }
            is ScreenStateHelper.Success -> {
                if(state.data != null) {
                    val characterVoice = state.data
                    characterVoiceRecyclerView.setHasFixedSize(true)
                    characterVoiceRecyclerView.setItemViewCacheSize(10)
                    actorAdapter = ActorAdapter()
                    actorAdapter.submitList(characterVoice)
                    characterVoiceRecyclerView.layoutManager = GridLayoutManager(context, 2 , GridLayoutManager.VERTICAL, false)
                    characterVoiceRecyclerView.adapter = actorAdapter

                    shimmerLayout.stopShimmer()
                    shimmerLayout.visibility = View.GONE
                    characterVoiceRecyclerView.visibility = View.VISIBLE

                    Log.i(TAG, "Success in loading character voice")
                }
            }
            is ScreenStateHelper.Empty -> {
                emptyText.text = state.message
                emptyText.visibility = View.VISIBLE
                characterVoiceRecyclerView.visibility = View.GONE
            }
            is ScreenStateHelper.Error -> {
                Log.e(TAG, "Error CharacterVoiceFragment in CharacterVoiceFragment with code ${state.message}")
            }
        }
    }
}