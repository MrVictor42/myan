package com.victor.myan.screens.characterDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.victor.myan.R
import com.victor.myan.adapter.CharacterDetailViewPagerAdapter
import com.victor.myan.databinding.FragmentBaseCharacterDetailBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Character
import com.victor.myan.screens.HomeFragment
import com.victor.myan.viewmodel.CharacterViewModel

class BaseCharacterDetailFragment : Fragment() {

    private lateinit var binding : FragmentBaseCharacterDetailBinding

    companion object {
        fun newInstance(mal_id : String): BaseCharacterDetailFragment {
            val baseFragment = BaseCharacterDetailFragment()
            val args = Bundle()
            args.putString("mal_id", mal_id)
            baseFragment.arguments = args
            return baseFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseCharacterDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getString("mal_id").toString()
        val viewModel : CharacterViewModel by viewModels { CharacterViewModel.CharacterFactory(malID) }
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager2
        val sizePager = 3
        val adapter = CharacterDetailViewPagerAdapter(parentFragmentManager, lifecycle, malID, sizePager)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager){tab, position ->
            when(position) {
                0 -> tab.text = "Overview"
                1 -> tab.text = "Anime | Manga"
                2 -> tab.text = "Voices"
            }
        }.attach()

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val homeFragment = HomeFragment.newInstance()
                val fragmentManager = activity?.supportFragmentManager
                fragmentManager?.popBackStack()
                fragmentManager?.beginTransaction()?.replace(R.id.content, homeFragment)
                    ?.addToBackStack(null)?.commit()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        viewModel.characterLiveData.observe(this, { state ->
            processCharacterResponse(state)
        })
    }

    private fun processCharacterResponse(state: ScreenStateHelper<Character>?) {

//        val aboutText = binding.about
//
//        when(state) {
//            is ScreenStateHelper.Loading -> {
//
//            }
//            is ScreenStateHelper.Success -> {
//                if(state.data != null) {
//                    aboutText.text = state.data.about
//                }
//            }
//            is ScreenStateHelper.Error -> {
////                Snackbar.make(view, "Not found characters ...", Snackbar.LENGTH_LONG).show()
//            }
//        }
    }
}