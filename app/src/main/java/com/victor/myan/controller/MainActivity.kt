package com.victor.myan.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import com.victor.myan.R
import com.victor.myan.layouts.BaseLayout

class MainActivity : AppCompatActivity() {

    private lateinit var userController: UserController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userController = UserController()
        setContentView(R.layout.activity_main)

        val w: Window = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        if(userController.userIsAuthenticated()) {
            val intentHome = Intent(this, BaseLayout::class.java)
            startActivity(intentHome)
            finish()
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                val slidesIntent = Intent(this, SlidesActivity::class.java)
                startActivity(slidesIntent)
                finish()
            }, 2000)
        }
    }
}