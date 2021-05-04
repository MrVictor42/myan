package com.victor.myan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.victor.myan.controller.UserController
import com.victor.myan.layouts.BaseLayout

class MainActivity : AppCompatActivity() {

    private lateinit var userController: UserController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userController = UserController()
        setContentView(R.layout.activity_main)

        if(userController.userIsAuthenticated()) {
            val intentHome = Intent(this, BaseLayout::class.java)
            startActivity(intentHome)
            finish()
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                val slidesIntent = Intent(this, Slides::class.java)
                startActivity(slidesIntent)
                finish()
            }, 2000)
        }
    }
}