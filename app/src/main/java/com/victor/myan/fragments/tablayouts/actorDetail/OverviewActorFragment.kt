package com.victor.myan.fragments.tablayouts.actorDetail

import android.os.Bundle
import android.util.Log
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
import java.util.*

class OverviewActorFragment : Fragment() {

    private lateinit var binding : FragmentOverviewActorBinding
    private val actorViewModel by lazy {
        ViewModelProvider(this).get(ActorViewModel::class.java)
    }
    private val TAG = OverviewActorFragment::class.java.simpleName

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
        val givenNameText = binding.givenNameText
        val familyNameText = binding.familyNameText
        val personBirthday = binding.personBirthdayText
        val personAge = binding.personAgeText
        val alternativeNames = binding.alternativesNames
        val expandableAbout = binding.expandableTextViewAbout.expandableTextView
        val overviewActor = binding.overviewActor
        val shimmerLayout = binding.shimmerLayout

        when(state) {
            is ScreenStateHelper.Loading -> {
                shimmerLayout.startShimmer()
                Log.i(TAG, "Starting OverviewActorFragment")
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

                        if(name.isNullOrEmpty() || name == "null") {
                            personName.visibility = View.GONE
                        } else {
                            personName.text = name
                        }

                        if(givenName.isNullOrEmpty() || givenName == "null") {
                            givenNameText.visibility = View.GONE
                        } else {
                            givenNameText.text = givenName
                        }

                        if(familyName.isNullOrEmpty() || familyName == "null") {
                            familyNameText.visibility = View.GONE
                        } else {
                            familyNameText.text = familyName
                        }

                        if(birthday.isNullOrEmpty() || birthday == "null") {
                            personBirthday.visibility = View.GONE
                            personAge.visibility = View.GONE
                        } else {
                            val birthday = birthday.substring(0,10)
                            val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                            val age = currentYear - Integer.parseInt(birthday.substring(0,4))

                            personBirthday.text = birthday
                            personAge.text = age.toString()
                        }

                        if(alternateNames.isNullOrEmpty() || alternateNames.equals("null")) {
                            alternativeNames.visibility = View.GONE
                        } else {
                            alternativeNames.text = alternateNames.toString()
                        }

                        expandableAbout.text = about
                        shimmerLayout.stopShimmer()
                        shimmerLayout.visibility = View.GONE
                        overviewActor.visibility = View.VISIBLE

                        Log.i(TAG, "OverviewActorFragment with Success")
                    }
                }
            }
            is ScreenStateHelper.Error -> {
                Log.e(TAG, "Error OverviewActorFragment with code ${state.message}")
            }
            else -> {

            }
        }
    }
}