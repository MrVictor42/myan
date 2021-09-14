package com.victor.myan.fragments.tablayouts.genreDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.myan.databinding.FragmentCompleteBinding

class CompleteFragment : Fragment() {

    private lateinit var binding : FragmentCompleteBinding

    companion object {
        fun newInstance(mal_id : Int, type : String): CompleteFragment {
            val completeFragment = CompleteFragment()
            val args = Bundle()
            args.putInt("mal_id", mal_id)
            args.putString("type", type)
            completeFragment.arguments = args
            return completeFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompleteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}