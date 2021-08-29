package com.victor.myan.screens.characterDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.myan.R
import com.victor.myan.databinding.FragmentVoiceCharacterBinding

class VoiceCharacterFragment : Fragment() {

    private lateinit var binding : FragmentVoiceCharacterBinding

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
    }
}