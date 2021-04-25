package com.victor.myan.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.victor.myan.R

class FormLoginController : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_login_controller)

        supportActionBar!!.hide()
    }
}