package com.victor.myan.screens.personDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.victor.myan.databinding.FragmentOverviewPersonBinding
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Actor
import com.victor.myan.viewmodel.ActorViewModel

class OverviewPersonFragment : Fragment() {

    private lateinit var binding : FragmentOverviewPersonBinding

    companion object {
        fun newInstance(mal_id : String): OverviewPersonFragment {
            val overviewFragment = OverviewPersonFragment()
            val args = Bundle()
            args.putString("mal_id", mal_id)
            overviewFragment.arguments = args
            return overviewFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOverviewPersonBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val malID = arguments?.getString("mal_id").toString()
        val viewModel : ActorViewModel by viewModels { ActorViewModel.ActorFactory(malID) }

        viewModel.actorLiveData.observe(this, { state ->
            processActorResponse(state)
        })
    }

    private fun processActorResponse(state: ScreenStateHelper<Actor>?) {

        val personImage = binding.personImage
        val personName = binding.personName
        val givenName = binding.givenName
        val familyName = binding.familyName
        val personBirthday = binding.personBirthday
        val alternativeNames = binding.alternativesNames
        val expandableAbout = binding.expandableTextViewAbout.expandableTextView

        when(state) {
            is ScreenStateHelper.Loading -> {

            }
            is ScreenStateHelper.Success -> {
                if(state.data != null) {
                    with(state.data) {
                        Glide.with(view?.context!!).load(image_url).into(personImage)
                        personName.text = name
                        personName.visibility = View.VISIBLE
                        givenName.text = given_name
                        givenName.visibility = View.VISIBLE
                        familyName.text = family_name
                        familyName.visibility = View.VISIBLE
                        personBirthday.text = birthday
                        personBirthday.visibility = View.VISIBLE

                        if(alternate_names.isEmpty() || alternate_names.equals("null")) {
                            // Nothing to do
                        } else {
                            alternativeNames.text = alternate_names.toString()
                            alternativeNames.visibility = View.VISIBLE
                        }

                        expandableAbout.text = about
                        expandableAbout.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}