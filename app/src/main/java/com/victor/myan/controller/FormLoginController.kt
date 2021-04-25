package com.victor.myan.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.victor.myan.R
import com.victor.myan.databinding.ActivityFormLoginControllerBinding

class FormLoginController : AppCompatActivity() {

    private lateinit var binding: ActivityFormLoginControllerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginControllerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        window.statusBarColor = this.resources.getColor(R.color.white)

        binding.registerUserText.setOnClickListener {
            val formRegisterUserIntent = Intent(this, FormRegisterUser::class.java)
            startActivity(formRegisterUserIntent)
        }
    }
}