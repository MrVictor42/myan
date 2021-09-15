package com.victor.myan.fragments.tablayouts.genreDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.myan.databinding.FragmentAiringBinding

class AiringFragment : Fragment() {

    private lateinit var binding : FragmentAiringBinding

    companion object {
        fun newInstance(mal_id : Int, type : String): AiringFragment {
            val airingFragment = AiringFragment()
            val args = Bundle()
            args.putInt("mal_id", mal_id)
            args.putString("type", type)
            airingFragment.arguments = args
            return airingFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAiringBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e("args", arguments.toString())
        val selected = arguments?.getString("type")
        val text = binding.teste
        val text2 = binding.teste2

        text.text = selected
        text2.text = "TEXTO 2"
    }
}