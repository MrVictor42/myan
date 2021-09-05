package com.victor.myan.screens.characterDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.victor.myan.databinding.FragmentOverviewCharacterBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Character
import com.victor.myan.viewmodel.CharacterViewModel

class OverviewCharacterFragment : Fragment() {

    private lateinit var binding : FragmentOverviewCharacterBinding

    companion object {
        fun newInstance(mal_id : String): OverviewCharacterFragment {
            val overviewFragment = OverviewCharacterFragment()
            val args = Bundle()
            args.putString("mal_id", mal_id)
            overviewFragment.arguments = args
            return overviewFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentOverviewCharacterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getString("mal_id").toString()
//        val viewModel : CharacterViewModel by viewModels { CharacterViewModel.CharacterFactory(malID) }
//
//        viewModel.characterLiveData.observe(this, { state ->
//            processCharacterResponse(state)
//        })
    }

    private fun processCharacterResponse(state: ScreenStateHelper<Character>?) {

        val characterName = binding.characterName
        val characterNameKanji = binding.characterNameKanji
        val characterImage = binding.characterImage
        val characterNickname = binding.characterNickname
        val characterAbout = binding.expandableTextViewAbout.expandableTextView

        when(state) {
            is ScreenStateHelper.Loading -> {

            }
            is ScreenStateHelper.Success -> {
                if(state.data != null) {
                    with(state.data) {
                        Glide.with(view?.context!!).load(image_url).into(characterImage)

                        if(name.isEmpty()) {
                            // Nothing to do
                        } else {
                            characterName.visibility = View.VISIBLE
                            characterName.text = name
                        }

                        if(name_kanji.isEmpty()) {
                            // Nothing to do
                        } else {
                            characterNameKanji.visibility = View.VISIBLE
                            characterNameKanji.text = name_kanji
                        }

                        if(nicknames.isEmpty()) {
                            // Nothing to do
                        } else {
                            characterNickname.visibility = View.VISIBLE
                            characterNickname.text = nicknames.toString()
                        }

                        characterAbout.text = about
                    }
                }
            }
            is ScreenStateHelper.Error -> {
                val overviewCharacterFragment = binding.fragmentOverviewCharacter
                Snackbar.make(overviewCharacterFragment, "Not found information about this character...", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}