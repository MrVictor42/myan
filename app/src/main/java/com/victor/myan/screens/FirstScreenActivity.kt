package com.victor.myan.screens

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.victor.myan.R
import com.victor.myan.layouts.BaseLayout

class FirstScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_screen_activity)

        val w: Window = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        if(userHasConnection()) {
            if(userIsAuthenticated()) {
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
            Toast.makeText(this, "Connection False", Toast.LENGTH_SHORT).show()
        }
    }

    private fun userIsAuthenticated(): Boolean {
        return FirebaseAuth.getInstance().currentUser != null
    }

    private fun userHasConnection(): Boolean {
        val cm =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo

        return activeNetwork?.isConnectedOrConnecting == true
    }
}