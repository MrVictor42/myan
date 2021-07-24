package com.victor.myan.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.victor.myan.R
import com.victor.myan.databinding.ActivityForgotPasswordBinding
import com.victor.myan.helper.AuxFunctionsHelper

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding : ActivityForgotPasswordBinding
    private val auxFunctionsHelper = AuxFunctionsHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(supportActionBar != null) {
            supportActionBar!!.hide()
        }
        window.statusBarColor =  ContextCompat.getColor(this, R.color.black)

        val btnResetPassword = binding.btnResetPassword


        btnResetPassword.setOnClickListener {
            val email = binding.editEmail.text.toString().trim()
            val progressBar = binding.progressBar
            val mAuth = FirebaseAuth.getInstance()

            if(!auxFunctionsHelper.validateField(email, binding.editEmail)) {
                return@setOnClickListener
            } else {
                progressBar.visibility = View.VISIBLE
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                    if(it.isSuccessful) {
                        Toast.makeText(
                            this,
                            auxFunctionsHelper.capitalize("check your email to reset your password!"),
                            Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(this, FormLoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            auxFunctionsHelper.capitalize(
                                "try again! something wrong happened!"),
                            Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
}