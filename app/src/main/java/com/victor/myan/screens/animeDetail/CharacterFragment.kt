package com.victor.myan.screens.animeDetail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.victor.myan.adapter.CharactersAdapter
import com.victor.myan.api.CharacterApi
import com.victor.myan.databinding.FragmentCharacterBinding
import com.victor.myan.helper.JikanApiInstanceHelper
import com.victor.myan.model.Character
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterFragment : Fragment() {

    private lateinit var binding : FragmentCharacterBinding
    private lateinit var characterAdapter : CharactersAdapter

    companion object {
        fun newInstance(mal_id : String): CharacterFragment {
            val characterFragment = CharacterFragment()
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
        binding = FragmentCharacterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val characterRecyclerView = binding.animeCharacter
        val characterList = arrayListOf<Character>()
        val malID = arguments?.getString("mal_id").toString()
        val characterApi = JikanApiInstanceHelper.getJikanApiInstance().create(CharacterApi::class.java)

        characterRecyclerView.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        characterAdapter = CharactersAdapter(characterList)
        characterRecyclerView.adapter = characterAdapter

        characterApi.getCharactersStaff(malID).enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val charactersResponse = response.body()
                    characterAdapter.character.clear()
                    if (charactersResponse != null) {
                        val animeCharacters : JsonArray? =
                            charactersResponse.getAsJsonArray("characters")
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