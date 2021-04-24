package com.victor.myan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.hide()
        val window = this.window
        window.statusBarColor = this.resources.getColor(R.color.orange)
        Handler(Looper.getMainLooper()).postDelayed({
            val slidesIntent = Intent(this, Slides::class.java)
            startActivity(slidesIntent)
            finish()
        }, 2000)
    }
}