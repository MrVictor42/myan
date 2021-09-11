package com.victor.myan.fragments.tablayouts.characterDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.victor.myan.R
import com.victor.myan.databinding.FragmentOverviewCharacterBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Character
import com.victor.myan.viewmodel.CharacterViewModel

class OverviewCharacterFragment : Fragment() {

    private lateinit var binding : FragmentOverviewCharacterBinding
    private val characterViewModel by lazy {
        ViewModelProvider(this).get(CharacterViewModel::class.java)
    }

    companion object {
        fun newInstance(mal_id : Int): OverviewCharacterFragment {
            val overviewFragment = OverviewCharacterFragment()
            val args = Bundle()
            args.putInt("mal_id", mal_id)
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
        val malID = arguments?.getInt("mal_id")!!
        Log.e("MAL ID CHARACTER", malID.toString())

        characterViewModel.getCharacterApi(malID)
        characterViewModel.character.observe(viewLifecycleOwner, { state ->
            processCharacterResponse(state)
        })
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
                        Glide.with(view?.context!!)
                            .load(imageUrl)
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .error(R.drawable.ic_launcher_foreground)
                            .fallback(R.drawable.ic_launcher_foreground)
                            .fitCenter()
                            .into(characterImage)

                        if(name.isNullOrEmpty()) {
                            // Nothing to do
                        } else {
                            characterName.visibility = View.VISIBLE
                            characterName.text = name
                        }

                        if(nameKanji.isNullOrEmpty()) {
                            // Nothing to do
                        } else {
                            characterNameKanji.visibility = View.VISIBLE
                            characterNameKanji.text = nameKanji
                        }

                        if(nicknames.isNullOrEmpty()) {
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

            }
            else -> {

            }
        }
    }
}