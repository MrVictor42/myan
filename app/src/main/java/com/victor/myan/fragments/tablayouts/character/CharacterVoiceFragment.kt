package com.victor.myan.fragments.tablayouts.character

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.victor.myan.adapter.ActorAdapter
import com.victor.myan.databinding.FragmentCharacterVoiceBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.viewmodel.CharacterViewModel

class CharacterVoiceFragment : Fragment() {

    private lateinit var binding : FragmentCharacterVoiceBinding
    private lateinit var actorAdapter: ActorAdapter
    private val characterViewModel by lazy {
        ViewModelProvider(this)[CharacterViewModel::class.java]
    }

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
        val characterVoiceRecyclerView = binding.recyclerView
        val emptyText = binding.emptyListTextView
        val shimmerLayout = binding.shimmerLayout

        characterViewModel.getCharacterVoiceApi(malID)
        characterViewModel.characterVoiceList.observe(viewLifecycleOwner, { voice ->
            when(voice) {
                is ScreenStateHelper.Loading -> {
                    shimmerLayout.startShimmer()
                }
                is ScreenStateHelper.Success -> {
                    if(voice.data != null) {
                        val characterVoiceList = voice.data
                        characterVoiceRecyclerView.layoutManager =
                            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                        actorAdapter = ActorAdapter()
                        actorAdapter.setData(characterVoiceList)
                        characterVoiceRecyclerView.adapter = actorAdapter
                        shimmerLayout.stopShimmer()
                        shimmerLayout.visibility = View.GONE
                        characterVoiceRecyclerView.visibility = View.VISIBLE
                    }
                }
                is ScreenStateHelper.Empty -> {
                    emptyText.text = voice.message
                    emptyText.visibility = View.VISIBLE
                    characterVoiceRecyclerView.visibility = View.GONE
                }
                is ScreenStateHelper.Error -> {

                }
            }
        })
    }
}