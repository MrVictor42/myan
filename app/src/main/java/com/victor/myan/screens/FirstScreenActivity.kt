package com.victor.myan.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import com.victor.myan.R
import com.victor.myan.helper.AuxFunctionsHelper
import com.victor.myan.layouts.BaseLayout

class FirstScreenActivity : AppCompatActivity() {

    private val auxFunctionsHelper = AuxFunctionsHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_screen_activity)

        val w: Window = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

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