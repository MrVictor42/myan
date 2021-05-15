package com.victor.myan.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.victor.myan.R
import com.victor.myan.databinding.ActivityVideoBinding
import com.victor.myan.fragments.HomeFragment
import java.net.URI

class VideoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            val animeTrailer = Uri.parse(it.getString("animeTrailer").toString())
            val video = binding.animeTrailer
            video.setMediaController(MediaController(this))
            video.setVideoURI(animeTrailer)
            video.requestFocus()
            video.start()
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.e("OnBackPressCallBack", "true")
            }
        }
    }
}