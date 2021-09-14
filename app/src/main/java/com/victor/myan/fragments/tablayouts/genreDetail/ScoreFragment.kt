package com.victor.myan.fragments.tablayouts.genreDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.myan.databinding.FragmentScoreBinding

class ScoreFragment : Fragment() {

    private lateinit var binding : FragmentScoreBinding

    companion object {
        fun newInstance(mal_id : Int, type : String): ScoreFragment {
            val scoreFragment = ScoreFragment()
            val args = Bundle()
            args.putInt("mal_id", mal_id)
            args.putString("type", type)
            scoreFragment.arguments = args
            return scoreFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScoreBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}