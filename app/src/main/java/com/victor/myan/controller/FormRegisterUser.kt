package com.victor.myan.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.victor.myan.R
import com.victor.myan.databinding.ActivityFormRegisterUserBinding

class FormRegisterUser : AppCompatActivity() {

    private lateinit var binding: ActivityFormRegisterUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        window.statusBarColor = this.resources.getColor(R.color.white)
    }
}