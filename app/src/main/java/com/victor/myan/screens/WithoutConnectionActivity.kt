package com.victor.myan.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import com.bumptech.glide.Glide
import com.victor.myan.R
import com.victor.myan.databinding.ActivityWithoutConnectionBinding
import com.victor.myan.helper.AuxFunctionsHelper

class WithoutConnectionActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWithoutConnectionBinding
    private val auxFunctionsHelper = AuxFunctionsHelper()

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWithoutConnectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageGif = binding.sadGif
        val withoutConnectionText = binding.withoutConnectionText
        val btnUpdate = binding.btnUpdate
        val messageError = binding.messageError

        Glide.with(this).load(R.drawable.sad_naruto).into(imageGif)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            withoutConnectionText.justificationMode = Layout.JUSTIFICATION_MODE_INTER_WORD
            withoutConnectionText.text = auxFunctionsHelper.capitalize(
                "you haven't connection Wifi/4G in this moment, please able some " +
                        "connection and try again"
            )
        } else {
            withoutConnectionText.text = auxFunctionsHelper.capitalize("without connection!")
        }

        btnUpdate.setOnClickListener {
            if(auxFunctionsHelper.userHasConnection(this)) {
                if(auxFunctionsHelper.userIsAuthenticated()) {
                    val intent = Intent(this, BaseLayout::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this, FormLoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            } else {
                messageError.text = auxFunctionsHelper.capitalize("without connection!")
            }
        }
    }
}