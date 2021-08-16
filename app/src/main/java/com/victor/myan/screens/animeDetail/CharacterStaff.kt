package com.victor.myan.screens.animeDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.myan.R
import com.victor.myan.databinding.FragmentCharacterStaffBinding

class CharacterStaff : Fragment() {

    private lateinit var binding : FragmentCharacterStaffBinding

    companion object {
        fun newInstance(mal_id : String): CharacterStaff {
            val characterStaffFragment = CharacterStaff()
            val args = Bundle()
            args.putString("mal_id", mal_id)
            characterStaffFragment.arguments = args
            return characterStaffFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterStaffBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}