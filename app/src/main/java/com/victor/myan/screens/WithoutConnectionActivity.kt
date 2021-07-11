package com.victor.myan.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.victor.myan.R
import com.victor.myan.databinding.ActivityWithoutConnectionBinding
import com.victor.myan.helper.AuxFunctionsHelper

class WithoutConnectionActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWithoutConnectionBinding
    private val auxFunctionsHelper = AuxFunctionsHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWithoutConnectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(supportActionBar != null) {
            supportActionBar!!.hide()
        }

        val imageGif = binding.sadGif
        val withoutConnectionText = binding.withoutConnectionText
        val btnUpdate = binding.btnUpdate
        val messageError = binding.messageError

        Glide.with(this).load(R.drawable.sad_naruto).into(imageGif)

        withoutConnectionText.text = auxFunctionsHelper.capitalize(
            "you haven't connection Wifi/4G in this moment, please able some " +
                    "connection and try again"
        )

        btnUpdate.setOnClickListener {
            if(auxFunctionsHelper.userHasConnection(this)) {
                if(auxFunctionsHelper.userIsAuthenticated()) {
                    val intent = Intent(this, BaseLayout::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this, PresentationActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            } else {
                messageError.text = auxFunctionsHelper.capitalize("without connection!")
            }
        }
    }
}