package com.victor.myan.screens.animeDetail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.victor.myan.adapter.CharactersAdapter
import com.victor.myan.adapter.StaffAdapter
import com.victor.myan.api.CharactersStaffApi
import com.victor.myan.databinding.FragmentCharacterStaffBinding
import com.victor.myan.helper.JikanApiInstanceHelper
import com.victor.myan.model.Character
import com.victor.myan.model.Staff
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterStaffFragment : Fragment() {

    private lateinit var binding : FragmentCharacterStaffBinding
    private lateinit var characterAdapter : CharactersAdapter
    private lateinit var staffAdapter : StaffAdapter

    companion object {
        fun newInstance(mal_id : String): CharacterStaffFragment {
            val characterFragment = CharacterStaffFragment()
            val args = Bundle()
            args.putString("mal_id", mal_id)
            characterFragment.arguments = args
            return characterFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterStaffBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val characterRecyclerView = binding.animeCharacter
        val staffRecyclerView = binding.animeStaff
        val staffList = arrayListOf<Staff>()
        val characterList = arrayListOf<Character>()
        val malID = arguments?.getString("mal_id").toString()
        val characterStaffApi = JikanApiInstanceHelper.getJikanApiInstance().create(CharactersStaffApi::class.java)

        characterRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        characterAdapter = CharactersAdapter(characterList)
        characterRecyclerView.adapter = characterAdapter

        staffRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        staffAdapter = StaffAdapter(staffList)
        staffRecyclerView.adapter = staffAdapter

        characterStaffApi.getCharactersStaff(malID).enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val staffResponse = response.body()
                    characterAdapter.character.clear()
                    if (staffResponse != null) {
                        val animeCharacters : JsonArray? =
                            staffResponse.getAsJsonArray("characters")
                        if (animeCharacters != null) {
                            for (characters in 0 until animeCharacters.size()) {
                                val characterObject : JsonObject? =
                                    animeCharacters.get(characters) as JsonObject?
                                if (characterObject != null) {
                                    val character = Character()

                                    character.mal_id =
                                        characterObject.get("mal_id").asInt
                                    character.image_url =
                                        characterObject.get("image_url").asString
                                    character.name =
                                        characterObject.get("name").asString

                                    characterAdapter.character.add(character)

                                    val animeStaff : JsonArray? = characterObject.getAsJsonArray("voice_actors")
                                    if (animeStaff != null) {
                                        for (staffs in 0 until animeStaff.size()) {
                                            val staffObject : JsonObject? = animeStaff.get(staffs) as JsonObject?
                                            if(staffObject != null) {
                                                val staff = Staff()

                                                staff.mal_id = staffObject.get("mal_id").asInt
                                                staff.image_url = staffObject.get("image_url").asString
                                                staff.name = staffObject.get("name").asString

                                                staffAdapter.staff.add(staff)
                                            }
                                        }
                                        staffAdapter.notifyDataSetChanged()
                                    }
                                }
                            }
                            characterAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        })
    }
}