package com.victor.myan.fragments.tablayouts.character

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.victor.myan.R
import com.victor.myan.databinding.FragmentOverviewCharacterBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.viewmodel.CharacterViewModel

class OverviewCharacterFragment : Fragment() {

    private lateinit var binding : FragmentOverviewCharacterBinding
    private val characterViewModel by lazy {
        ViewModelProvider(this)[CharacterViewModel::class.java]
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOverviewCharacterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getInt("mal_id")!!
        val characterName = binding.characterName
        val characterNameKanji = binding.characterNameKanji
        val characterImage = binding.characterImage
        val characterNickname = binding.characterNickname
        val characterAbout = binding.expandableTextViewAbout.expandableTextView
        val shimmerLayout = binding.shimmerLayout
        val overviewCharacter = binding.overviewCharacter
        val errorOptions = binding.errorOptions.errorOptions
        val btnRefresh = binding.errorOptions.btnRefresh

        characterViewModel.getCharacterApi(malID)
        characterViewModel.character.observe(viewLifecycleOwner, { character ->
            when(character) {
                is ScreenStateHelper.Loading -> {
                    shimmerLayout.startShimmer()
                }
                is ScreenStateHelper.Success -> {
                    if(character.data != null) {
                        with(character.data) {
                            Glide.with(view.context!!)
                                .load(imageURL)
                                .placeholder(R.drawable.ic_launcher_foreground)
                                .error(R.drawable.ic_launcher_foreground)
                                .fallback(R.drawable.ic_launcher_foreground)
                                .fitCenter()
                                .into(characterImage)

                            if(name.isNullOrEmpty() || name == "null") {
                                characterName.visibility = View.GONE
                            } else {
                                characterName.text = name
                            }

                            if(nameKanji.isNullOrEmpty() || nameKanji == "null") {
                                characterNameKanji.visibility = View.GONE
                            } else {
                                characterNameKanji.text = nameKanji
                            }

                            if(nicknames.isNullOrEmpty() || nicknames.equals("null")) {
                                characterNickname.visibility = View.GONE
                            } else {
                                characterNickname.text = nicknames.toString()
                            }

                            characterAbout.text = about
                            shimmerLayout.stopShimmer()
                            shimmerLayout.visibility = View.GONE
                            overviewCharacter.visibility = View.VISIBLE
                        }
                    }
                }
                is ScreenStateHelper.Error -> {
                    errorOptions.visibility = View.VISIBLE
                    shimmerLayout.stopShimmer()
                    shimmerLayout.visibility = View.GONE

                    btnRefresh.setOnClickListener {
                        onViewCreated(view, savedInstanceState)

                        errorOptions.visibility = View.GONE
                    }
                }
                else -> {

                }
            }
        })
    }
}