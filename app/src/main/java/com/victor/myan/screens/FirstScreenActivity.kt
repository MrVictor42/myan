package com.victor.myan.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import com.victor.myan.R
import com.victor.myan.helper.AuxFunctionsHelper

class FirstScreenActivity : AppCompatActivity() {

    private val auxFunctionsHelper = AuxFunctionsHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_screen_activity)

        if(supportActionBar != null) {
            supportActionBar!!.hide()
        }
        window.statusBarColor =  ContextCompat.getColor(this, R.color.black)

        if(auxFunctionsHelper.userHasConnection(this)) {
            if(auxFunctionsHelper.userIsAuthenticated()) {
                val intent = Intent(this, BaseLayout::class.java)
                startActivity(intent)
                finish()
            } else {
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this, PresentationActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 2000)
            }
        } else {
            val intent = Intent(this, WithoutConnectionActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}