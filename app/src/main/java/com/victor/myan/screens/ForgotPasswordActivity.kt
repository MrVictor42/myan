package com.victor.myan.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.victor.myan.R
import com.victor.myan.databinding.ActivityForgotPasswordBinding
import com.victor.myan.helper.AuxFunctionsHelper

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding : ActivityForgotPasswordBinding
    private lateinit var progressBar : ProgressBar
    private val auxFunctionsHelper = AuxFunctionsHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(supportActionBar != null) {
            supportActionBar!!.hide()
        }
        window.statusBarColor =  ContextCompat.getColor(this, R.color.black)
        progressBar = binding.progressBar
        progressBar.visibility = View.GONE

        val btnResetPassword = binding.btnResetPassword

        btnResetPassword.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val progressBar = binding.progressBar
            val mAuth = FirebaseAuth.getInstance()

            if(!auxFunctionsHelper.validateField(email, binding.editTextEmail)) {
                return@setOnClickListener
            } else {
                progressBar.visibility = View.VISIBLE
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                    if(it.isSuccessful) {
                        Snackbar.make(
                            binding.activityForgotPassword,
                            auxFunctionsHelper.capitalize("check your email to reset your password!"),
                            Snackbar.LENGTH_LONG
                        ).show()
                        val intent = Intent(this, FormLoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Snackbar.make(
                            binding.activityForgotPassword,
                            auxFunctionsHelper.capitalize(
                                "try again! something wrong happened!"),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}