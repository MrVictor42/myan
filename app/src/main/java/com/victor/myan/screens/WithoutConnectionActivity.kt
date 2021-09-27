package com.victor.myan.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.victor.myan.R
import com.victor.myan.baseFragments.BaseLayout
import com.victor.myan.databinding.ActivityWithoutConnectionBinding
import com.victor.myan.helper.AuxFunctionsHelper

class WithoutConnectionActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWithoutConnectionBinding
    private val auxFunctionsHelper = AuxFunctionsHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWithoutConnectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(supportActionBar != null) {
            supportActionBar!!.hide()
        }
        window.statusBarColor =  ContextCompat.getColor(this, R.color.black)

        val btnRefresh = binding.btnRefresh

        btnRefresh.setOnClickListener {
            if(auxFunctionsHelper.userHasConnection(this)) {
                if(auxFunctionsHelper.userIsAuthenticated()) {
                    val intent = Intent(this, BaseLayout::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this, PresentationActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            } else {
                Snackbar.make(
                    binding.withoutConnection,
                    "Connection with internet not found...",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }
}