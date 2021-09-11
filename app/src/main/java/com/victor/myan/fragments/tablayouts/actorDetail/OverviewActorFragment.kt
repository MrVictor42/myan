package com.victor.myan.fragments.tablayouts.actorDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.victor.myan.R
import com.victor.myan.databinding.FragmentOverviewActorBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Actor
import com.victor.myan.viewmodel.ActorViewModel

class OverviewActorFragment : Fragment() {

    private lateinit var binding : FragmentOverviewActorBinding
    private val actorViewModel by lazy {
        ViewModelProvider(this).get(ActorViewModel::class.java)
    }

    companion object {
        fun newInstance(mal_id : Int): OverviewActorFragment {
            val overviewFragment = OverviewActorFragment()
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
        binding = FragmentOverviewActorBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getInt("mal_id")!!

        actorViewModel.getActorApi(malID)
        actorViewModel.actor.observe(viewLifecycleOwner, { state ->
            processActorResponse(state)
        })
    }

    private fun processActorResponse(state: ScreenStateHelper<Actor>?) {

        val personImage = binding.personImage
        val personName = binding.personName
        val givenNameText = binding.givenName
        val familyNameText = binding.familyName
        val personBirthday = binding.personBirthday
        val alternativeNames = binding.alternativesNames
        val expandableAbout = binding.expandableTextViewAbout.expandableTextView

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
                            .into(personImage)
                        personName.text = name
                        personName.visibility = View.VISIBLE
                        givenNameText.text = givenName
                        givenNameText.visibility = View.VISIBLE
                        familyNameText.text = familyName
                        familyNameText.visibility = View.VISIBLE
                        personBirthday.text = birthday
                        personBirthday.visibility = View.VISIBLE

                        if(alternateNames.isNullOrEmpty() || alternateNames.equals("null")) {
                            // Nothing to do
                        } else {
                            alternativeNames.text = alternateNames.toString()
                            alternativeNames.visibility = View.VISIBLE
                        }

                        expandableAbout.text = about
                        expandableAbout.visibility = View.VISIBLE
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